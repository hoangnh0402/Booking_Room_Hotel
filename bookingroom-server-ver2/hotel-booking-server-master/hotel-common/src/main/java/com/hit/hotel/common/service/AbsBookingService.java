package com.hit.hotel.common.service;

import com.hit.cache.store.external.ExternalCacheStore;
import com.hit.common.core.exception.BaseResponseException;
import com.hit.common.core.exception.ResponseStatusCodeEnum;
import com.hit.common.utils.TimeUtils;
import com.hit.hotel.common.domain.mapper.BookingMapper;
import com.hit.hotel.common.domain.request.BookingRequest;
import com.hit.hotel.common.domain.request.BookingServiceRequest;
import com.hit.hotel.common.domain.response.booking.BookingDetailResponse;
import com.hit.hotel.common.domain.response.booking.BookingResponse;
import com.hit.hotel.common.domain.response.booking.BookingSurchargeResponse;
import com.hit.hotel.repository.booking.BookingStore;
import com.hit.hotel.repository.booking.constants.BookingStatus;
import com.hit.hotel.repository.booking.entity.Booking;
import com.hit.hotel.repository.bookingdetail.BookingRoomDetailStore;
import com.hit.hotel.repository.bookingdetail.BookingServiceDetailStore;
import com.hit.hotel.repository.bookingdetail.entity.BookingRoomDetail;
import com.hit.hotel.repository.bookingdetail.entity.BookingServiceDetail;
import com.hit.hotel.repository.notification.constant.NotificationTemplateEnum;
import com.hit.hotel.repository.room.RoomStore;
import com.hit.hotel.repository.room.entity.Room;
import com.hit.hotel.repository.sale.entity.Sale;
import com.hit.hotel.repository.service.ServiceStore;
import com.hit.hotel.repository.service.entity.Service;
import com.hit.hotel.repository.user.UserStore;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AbsBookingService {

    @Setter(onMethod_ = {@Autowired})
    protected NotificationService notificationService;

    @Setter(onMethod_ = {@Autowired})
    protected UserStore userStore;

    @Setter(onMethod_ = {@Autowired})
    protected BookingStore bookingStore;

    @Setter(onMethod_ = {@Autowired})
    protected RoomStore roomStore;

    @Setter(onMethod_ = {@Autowired})
    protected ServiceStore serviceStore;

    @Setter(onMethod_ = {@Autowired})
    protected BookingRoomDetailStore bookingRoomDetailStore;

    @Setter(onMethod_ = {@Autowired})
    protected BookingServiceDetailStore bookingServiceDetailStore;

    @Setter(onMethod_ = {@Autowired})
    protected ExternalCacheStore externalCacheStore;

    @Setter(onMethod_ = {@Autowired})
    protected BookingMapper bookingMapper;

    private static final String LOCK_BOOKING_ROOM_KEY = "LOCK_BOOKING_ROOM::%s";

    public static final LocalTime TIME_5H00 = LocalTime.of(5, 0);
    public static final LocalTime TIME_9H00 = LocalTime.of(9, 0);
    public static final LocalTime TIME_14H00 = LocalTime.of(14, 0);

    public static final LocalTime TIME_12H00 = LocalTime.of(12, 0);
    public static final LocalTime TIME_15H00 = LocalTime.of(15, 0);
    public static final LocalTime TIME_18H00 = LocalTime.of(18, 0);

    protected BookingResponse lockBooking(Map<Integer, Room> roomsBooking, Supplier<BookingResponse> handler) {
        List<String> keyLocks = roomsBooking.keySet().stream()
                .map(roomId -> String.format(LOCK_BOOKING_ROOM_KEY, roomId))
                .toList();
        BookingResponse response = externalCacheStore.lockAndHandle(keyLocks, handler);
        notificationService.pushNotification(NotificationTemplateEnum.BOOKING, response);
        notificationService.pushEventRoomStatus(
                response.getExpectedCheckIn(), response.getExpectedCheckOut(),
                response.getStatus(), roomsBooking.keySet()
        );
        return response;
    }

    protected Map<Integer, Room> validateRoomBookingRequest(BookingRequest request) {
        Map<Integer, Room> roomsBooking = roomStore.getMapId(request.getRoomIds());
        this.checkRoomNotFound(roomsBooking, request.getRoomIds());
        this.checkRoomAvailable(request.getExpectedCheckIn(), request.getExpectedCheckOut(), roomsBooking);
        LocalDate now = LocalDate.now();
        this.checkExpectedCheckIn(now, request.getExpectedCheckIn(), request.getExpectedCheckOut());
        this.checkExpectedCheckOut(now, request.getExpectedCheckIn(), request.getExpectedCheckOut());
        return roomsBooking;
    }

    protected BookingResponse toBookingResponse(Booking booking) {
        Double totalRoomPrice = this.calculateTotalRoomPrice(booking, booking.getBookingRoomDetails());
        Double totalServicePrice = this.calculateTotalServicePrice(booking.getBookingServiceDetails());
        long totalSurcharge = this.getBookingSurchargeResponses(booking, booking.getBookingRoomDetails())
                .stream().filter(Objects::nonNull)
                .mapToLong(BookingSurchargeResponse::getSurcharge).sum();
        return bookingMapper.toBookingResponse(booking, totalRoomPrice, totalServicePrice, totalSurcharge);
    }

    protected BookingDetailResponse toBookingDetailResponse(Booking booking) {
        Double totalRoomPrice = this.calculateTotalRoomPrice(booking, booking.getBookingRoomDetails());
        Double totalServicePrice = this.calculateTotalServicePrice(booking.getBookingServiceDetails());
        List<BookingSurchargeResponse> bookingSurchargeResponses =
                this.getBookingSurchargeResponses(booking, booking.getBookingRoomDetails());
        long totalSurcharge = bookingSurchargeResponses.stream().filter(Objects::nonNull)
                .mapToLong(BookingSurchargeResponse::getSurcharge).sum();
        return bookingMapper.toBookingDetailResponse(
                booking, bookingSurchargeResponses,
                totalRoomPrice, totalServicePrice, totalSurcharge
        );
    }

    protected void notificationCancelBooking(Booking booking) {
        Map<String, Object> dataNotification = new HashMap<>();
        dataNotification.put("id", booking.getId());
        dataNotification.put("note", booking.getNote());
        dataNotification.put("booker", bookingMapper.toBookerResponse(booking.getUser()));
        notificationService.pushNotification(NotificationTemplateEnum.CANCEL_BOOKING, dataNotification);
        List<Integer> roomIds = booking.getBookingRoomDetails().stream()
                .map(bookingRoomDetail -> bookingRoomDetail.getRoom().getId()).toList();
        notificationService.pushEventRoomStatus(
                booking.getExpectedCheckIn(), booking.getExpectedCheckOut(),
                booking.getStatus(), roomIds
        );
    }

    protected Set<BookingRoomDetail> getBookingRoomDetailsByBookingId(Integer bookingId) {
        return bookingRoomDetailStore.getAllByBookingId(bookingId);
    }

    protected Set<BookingRoomDetail> createBookingRoomDetails(Booking booking, Map<Integer, Room> roomBookings) {
        LocalDateTime now = LocalDateTime.now();
        Set<BookingRoomDetail> bookingRoomDetails = roomBookings.values().stream()
                .map(room -> {
                    BookingRoomDetail bookingRoomDetail = new BookingRoomDetail(booking, room);
                    bookingRoomDetail.setPrice(room.getPrice());
                    Sale sale = room.getSale();
                    if (ObjectUtils.isNotEmpty(sale) && Boolean.FALSE.equals(sale.getDeleteFlag())
                            && sale.getDayStart().isBefore(now) && now.isAfter(sale.getDayEnd())) {
                        bookingRoomDetail.setSalePercent(sale.getSalePercent());
                    }
                    return bookingRoomDetail;
                }).collect(Collectors.toSet());
        bookingRoomDetailStore.saveAll(bookingRoomDetails);
        return bookingRoomDetails;
    }

    protected Set<BookingServiceDetail> getBookingServiceDetailsByBooking(Integer bookingId) {
        return bookingServiceDetailStore.getAllByBookingId(bookingId);
    }

    protected Set<BookingServiceDetail> createBookingServiceDetails(Booking booking,
                                                                    List<BookingServiceRequest> servicesRequest) {
        List<Integer> serviceIds = servicesRequest.stream().map(BookingServiceRequest::getServiceId).toList();
        Map<Integer, Service> servicesBooking = serviceStore.getMapId(serviceIds);
        this.checkServiceNotFound(servicesBooking, serviceIds);
        Set<BookingServiceDetail> bookingServiceDetails = servicesRequest.stream()
                .map(serviceRequest -> {
                    Service service = servicesBooking.get(serviceRequest.getServiceId());
                    BookingServiceDetail serviceDetail = new BookingServiceDetail();
                    serviceDetail.setPrice(service.getPrice());
                    serviceDetail.setQuantity(serviceRequest.getQuantity());
                    serviceDetail.setBooking(booking);
                    serviceDetail.setService(service);
                    return serviceDetail;
                }).collect(Collectors.toSet());
        bookingServiceDetailStore.saveAll(bookingServiceDetails);
        return bookingServiceDetails;
    }

    protected Double calculateTotalRoomPrice(Booking booking, Set<BookingRoomDetail> bookingRoomDetails) {
        double totalRoomPrice = 0;
        Integer totalDay = TimeUtils.getDaysBetween(booking.getExpectedCheckIn(), booking.getExpectedCheckOut());
        for (BookingRoomDetail bookingRoomDetail : bookingRoomDetails) {
            if (bookingRoomDetail.getSalePercent() != null) {
                double salePrice = bookingRoomDetail.getPrice() * (bookingRoomDetail.getSalePercent() / 100f);
                totalRoomPrice += (bookingRoomDetail.getPrice() - salePrice) * totalDay;
            } else {
                totalRoomPrice += bookingRoomDetail.getPrice() * totalDay;
            }
        }
        return totalRoomPrice;
    }

    protected Double calculateTotalServicePrice(Set<BookingServiceDetail> bookingServiceDetails) {
        double totalServicePrice = 0L;
        for (BookingServiceDetail bookingServiceDetail : bookingServiceDetails) {
            totalServicePrice += (bookingServiceDetail.getPrice() * bookingServiceDetail.getQuantity());
        }
        return totalServicePrice;
    }

    private List<BookingSurchargeResponse> getBookingSurchargeResponses(Booking booking,
                                                                        Set<BookingRoomDetail> bookingRoomDetails) {
        if (!BookingStatus.CHECKED_IN.value().equals(booking.getStatus())
                || !BookingStatus.CHECKED_OUT.value().equals(booking.getStatus())) {
            return Collections.emptyList();
        }

        List<BookingSurchargeResponse> roomSurcharges = new LinkedList<>();
        if (BookingStatus.CHECKED_IN.value().equals(booking.getStatus()) && booking.getCheckIn() != null) {
            roomSurcharges.add(this.calculateTotalCheckInSurcharge(booking.getCheckIn(), bookingRoomDetails));
        }
        if (BookingStatus.CHECKED_OUT.value().equals(booking.getStatus()) && booking.getCheckOut() != null) {
            roomSurcharges.add(this.calculateTotalCheckOutSurcharge(booking.getCheckOut(), bookingRoomDetails));
        }
        return roomSurcharges;
    }

    private BookingSurchargeResponse calculateTotalCheckInSurcharge(LocalDateTime checkIn,
                                                                    Set<BookingRoomDetail> bookingRoomDetails) {
        LocalDate dateCheckIn = checkIn.toLocalDate();
        LocalDateTime date5h = LocalDateTime.of(dateCheckIn, TIME_5H00);
        LocalDateTime date9h = LocalDateTime.of(dateCheckIn, TIME_9H00);
        LocalDateTime date14h = LocalDateTime.of(dateCheckIn, TIME_14H00);
        BookingSurchargeResponse checkInSurcharge = new BookingSurchargeResponse();
        long totalCheckInSurcharge = 0L;
        if (checkIn.isBefore(date5h)) {
            checkInSurcharge.setReason("You check-in before 5h. You pay 100% more of the total room price");
            for (BookingRoomDetail bookingRoomDetail : bookingRoomDetails) {
                totalCheckInSurcharge += bookingRoomDetail.getPrice();
            }
        } else if (checkIn.isAfter(date5h) && checkIn.isBefore(date9h)) {
            checkInSurcharge.setReason("You check-in from 5h to 9h. You pay 50% more of the total room price");
            for (BookingRoomDetail bookingRoomDetail : bookingRoomDetails) {
                totalCheckInSurcharge += Math.round(bookingRoomDetail.getPrice() * 0.5);
            }
        } else if (checkIn.isAfter(date9h) && checkIn.isBefore(date14h)) {
            checkInSurcharge.setReason("You check-in from 9h to 14h. You pay 30% more of the total room price");
            for (BookingRoomDetail bookingRoomDetail : bookingRoomDetails) {
                totalCheckInSurcharge += Math.round(bookingRoomDetail.getPrice() * 0.3);
            }
        } else {
            return null;
        }
        checkInSurcharge.setSurcharge(totalCheckInSurcharge);
        return checkInSurcharge;
    }

    private BookingSurchargeResponse calculateTotalCheckOutSurcharge(LocalDateTime checkOut,
                                                                     Set<BookingRoomDetail> bookingRoomDetails) {
        LocalDate dateCheckOut = checkOut.toLocalDate();
        LocalDateTime date12h = LocalDateTime.of(dateCheckOut, TIME_12H00);
        LocalDateTime date15h = LocalDateTime.of(dateCheckOut, TIME_15H00);
        LocalDateTime date18h = LocalDateTime.of(dateCheckOut, TIME_18H00);
        long totalCheckOutSurcharge = 0L;
        BookingSurchargeResponse checkOutSurcharge = new BookingSurchargeResponse();
        if (checkOut.isAfter(date12h) && checkOut.isBefore(date15h)) {
            checkOutSurcharge.setReason("You check-out from 12h to 15h. You pay 30% more of the total room price");
            for (BookingRoomDetail bookingRoomDetail : bookingRoomDetails) {
                totalCheckOutSurcharge += Math.round(bookingRoomDetail.getPrice() * 0.3);
            }
        } else if (checkOut.isAfter(date15h) && checkOut.isBefore(date18h)) {
            checkOutSurcharge.setReason("You check-out from 15h to 18h. You pay 50% more of the total room price");
            for (BookingRoomDetail bookingRoomDetail : bookingRoomDetails) {
                totalCheckOutSurcharge += Math.round(bookingRoomDetail.getPrice() * 0.5);
            }
        } else if (checkOut.isAfter(date18h)) {
            checkOutSurcharge.setReason("You check-out after 18h. You pay 100% more of the total room price");
            for (BookingRoomDetail bookingRoomDetail : bookingRoomDetails) {
                totalCheckOutSurcharge += bookingRoomDetail.getPrice();
            }
        } else {
            return null;
        }
        checkOutSurcharge.setSurcharge(totalCheckOutSurcharge);
        return checkOutSurcharge;
    }

    private void checkRoomAvailable(LocalDateTime checkin, LocalDateTime checkout, Map<Integer, Room> roomsBooking) {
        Map<Integer, Boolean> roomsBooked = this.getRoomsBooked(checkin, checkout);
        String roomsUnavailable = roomsBooking.values().stream()
                .filter(roomBooking -> roomsBooked.containsKey(roomBooking.getId()))
                .map(Room::getName)
                .collect(Collectors.joining(","));
        if (StringUtils.isNotEmpty(roomsUnavailable)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.ROOMS_UNAVAILABLE, new String[]{roomsUnavailable});
        }
    }

    private Map<Integer, Boolean> getRoomsBooked(LocalDateTime checkin, LocalDateTime checkout) {
        return roomStore.getRoomsBooked(checkin, checkout).stream().collect(Collectors.toMap(Room::getId,
                room -> Boolean.TRUE));
    }

    private void checkRoomNotFound(Map<Integer, Room> roomsBooking, List<Integer> roomIds) {
        String roomIdsNotfound = roomIds.stream()
                .filter(roomId -> !roomsBooking.containsKey(roomId))
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        if (StringUtils.isNotEmpty(roomIdsNotfound)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.ROOMS_NOT_FOUND, new String[]{roomIdsNotfound});
        }
    }

    private void checkServiceNotFound(Map<Integer, Service> servicesBooking, List<Integer> serviceIds) {
        String servicesNotfound = serviceIds.stream()
                .filter(serviceId -> !servicesBooking.containsKey(serviceId))
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        if (StringUtils.isNotEmpty(servicesNotfound)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.SERVICES_NOT_FOUND,
                    new String[]{servicesNotfound});
        }
    }

    private void checkExpectedCheckIn(LocalDate now, LocalDateTime expectedCheckIn, LocalDateTime expectedCheckOut) {
        LocalDate checkInDate = expectedCheckIn.toLocalDate();
        LocalTime checkInTime = expectedCheckIn.toLocalTime();
        LocalDate checkOutDate = expectedCheckOut.toLocalDate();
        if (checkInDate.isAfter(checkOutDate) || checkInDate.equals(checkOutDate)
                || checkInDate.isBefore(now) || !checkInTime.equals(TIME_14H00)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.INVALID_DATE_CHECK_IN);
        }
    }

    private void checkExpectedCheckOut(LocalDate now, LocalDateTime expectedCheckIn, LocalDateTime expectedCheckOut) {
        LocalDate checkInDate = expectedCheckIn.toLocalDate();
        LocalDate checkOutDate = expectedCheckOut.toLocalDate();
        LocalTime checkOutTime = expectedCheckOut.toLocalTime();
        if (checkOutDate.isBefore(checkInDate) || checkOutDate.equals(checkInDate)
                || checkOutDate.isBefore(now) || checkOutDate.equals(now)
                || !checkOutTime.equals(TIME_12H00)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.INVALID_DATE_CHECK_OUT);
        }
    }

}
