package com.hit.hotel.admin.core.service.impl;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.json.JsonMapper;
import com.hit.hotel.admin.core.data.mapper.RoomMapper;
import com.hit.hotel.admin.core.data.mapper.UserMapper;
import com.hit.hotel.admin.core.data.request.StatisticRevenueRequest;
import com.hit.hotel.admin.core.data.response.RoomSummaryResponse;
import com.hit.hotel.admin.core.data.response.StatisticBookingResponse;
import com.hit.hotel.admin.core.data.response.StatisticRevenueResponse;
import com.hit.hotel.admin.core.data.response.UserResponse;
import com.hit.hotel.admin.core.service.StatisticService;
import com.hit.hotel.repository.booking.constants.BookingStatus;
import com.hit.hotel.repository.booking.entity.Booking;
import com.hit.hotel.repository.booking.model.condition.StatisticRevenueCondition;
import com.hit.hotel.repository.booking.model.projection.StatisticCustomerTopBookingProjection;
import com.hit.hotel.repository.bookingdetail.entity.BookingRoomDetail;
import com.hit.hotel.repository.bookingdetail.entity.BookingServiceDetail;
import com.hit.hotel.common.service.AbsBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service("statisticServiceAdmin")
public class StatisticServiceImpl extends AbsBookingService implements StatisticService {

    private final RoomMapper roomMapper;

    private final UserMapper userMapper;

    @Override
    public PaginationResponse<Map<String, Object>> statisticRoomBooked(PaginationRequest request,
                                                                       Integer month, Integer year) {
        return bookingRoomDetailStore.statisticRoomBooked(request, month, year).map(item -> {
            Map<String, Object> objectMap = new HashMap<>();
            RoomSummaryResponse roomSummary = roomMapper.toRoomSummaryResponse(item);
            objectMap.put("room", roomSummary);
            objectMap.put("value", item.getValue());
            return objectMap;
        });
    }

    @Override
    public List<Map<String, Object>> statisticCustomerTopBooking() {
        List<StatisticCustomerTopBookingProjection> customerTopBooking = bookingStore.getCustomersTopBooking();
        List<Map<String, Object>> result = new LinkedList<>();
        for (StatisticCustomerTopBookingProjection statisticCustomerTopBooking : customerTopBooking) {
            Map<String, Object> objectMap = new HashMap<>();
            UserResponse userResponse = userMapper.toUserResponse(statisticCustomerTopBooking);
            objectMap.put("user", userResponse);
            objectMap.put("value", statisticCustomerTopBooking.getValue());
            result.add(objectMap);
        }
        return result;
    }

    @Override
    @Transactional
    public List<StatisticRevenueResponse> statisticRevenue(StatisticRevenueRequest request) {
        List<StatisticRevenueResponse> revenueResponses = new LinkedList<>();
        int fromMonth = request.getFromMonth();
        int toMonth = request.getToMonth();
        List<Booking> bookings = bookingStore.statisticRevenue(
                JsonMapper.getObjectMapper().convertValue(request, StatisticRevenueCondition.class));
        for (int month = fromMonth; month <= toMonth; month++) {
            long totalRevenueMonth = 0;
            int totalBooking = 0;
            for (Booking booking : bookings) {
                if (booking.getCreatedDate().getMonthValue() == month) {
                    Set<BookingRoomDetail> bookingRoomDetails = booking.getBookingRoomDetails();
                    Set<BookingServiceDetail> bookingServiceDetails = booking.getBookingServiceDetails();
                    totalRevenueMonth += this.calculateTotalRoomPrice(booking, bookingRoomDetails);
                    totalRevenueMonth += this.calculateTotalServicePrice(bookingServiceDetails);
                    totalBooking++;
                }
            }
            StatisticRevenueResponse revenueResponse = new StatisticRevenueResponse();
            revenueResponse.setMonth(this.convertMonthNumberToString(month));
            revenueResponse.setTotalBooking(totalBooking);
            revenueResponse.setTotalRevenue(totalRevenueMonth);
            revenueResponses.add(revenueResponse);
        }
        return revenueResponses;
    }

    @Override
    public StatisticBookingResponse statisticBooking(Integer month, Integer year) {
        List<String> statisticBookingStatus = BookingStatus.getAll();
        StatisticBookingResponse response = new StatisticBookingResponse();
        bookingStore.statisticBookingByStatusIn(month, year, statisticBookingStatus)
                .forEach(item -> {
                    if (BookingStatus.CHECKED_IN.value().equals(item.getStatus())) {
                        response.setTotalBookingCheckin(item.getValue());
                    } else if (BookingStatus.CHECKED_OUT.value().equals(item.getStatus())) {
                        response.setTotalBookingCheckout(item.getValue());
                    } else if (BookingStatus.PENDING.value().equals(item.getStatus())) {
                        response.setTotalBookingPending(item.getValue());
                    } else if (BookingStatus.CANCEL.value().equals(item.getStatus())) {
                        response.setTotalBookingCancel(item.getValue());
                    }
                });
        return response;
    }

    private String convertMonthNumberToString(int monthNumber) {
        return switch (monthNumber) {
            case 1 -> "January";
            case 2 -> "February";
            case 3 -> "March";
            case 4 -> "April";
            case 5 -> "May";
            case 6 -> "June";
            case 7 -> "July";
            case 8 -> "August";
            case 9 -> "September";
            case 10 -> "October";
            case 11 -> "November";
            case 12 -> "December";
            default -> "";
        };
    }
}
