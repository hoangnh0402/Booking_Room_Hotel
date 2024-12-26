package com.hit.hotel.core.core.service.impl;

import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.common.core.exception.BaseResponseException;
import com.hit.common.core.exception.ResponseStatusCodeEnum;
import com.hit.common.utils.TimeUtils;
import com.hit.hotel.common.domain.request.BookingRequest;
import com.hit.hotel.common.domain.response.booking.BookingResponse;
import com.hit.hotel.common.service.AbsBookingService;
import com.hit.hotel.core.core.data.request.CancelBookingRequest;
import com.hit.hotel.core.core.service.BookingService;
import com.hit.hotel.repository.booking.constants.BookingStatus;
import com.hit.hotel.repository.booking.entity.Booking;
import com.hit.hotel.repository.bookingdetail.entity.BookingRoomDetail;
import com.hit.hotel.repository.bookingdetail.entity.BookingServiceDetail;
import com.hit.hotel.repository.room.entity.Room;
import com.hit.hotel.repository.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl extends AbsBookingService implements BookingService {

    @Override
    public PaginationResponse<BookingResponse> getBookings(PaginationRequest paginationRequest,
                                                           UserPrincipal principal) {
        if (StringUtils.isEmpty(paginationRequest.getSortBy())) {
            paginationRequest.setSortBy("createdDate");
            paginationRequest.setSortType(PaginationRequest.Direction.DESC.name());
        }
        return bookingStore.getBookingsByBookerId(paginationRequest, principal.getUser().getId())
                .map(this::toBookingResponse);
    }

    @Override
    @Transactional
    public BookingResponse booking(BookingRequest request, UserPrincipal principal) {
        Map<Integer, Room> roomsBooking = this.validateRoomBookingRequest(request);
        return this.lockBooking(roomsBooking, () -> {
            User booker = userStore.getUserByEmail(principal.getUser().getEmail());
            Booking booking = new Booking();
            booking.setExpectedCheckIn(request.getExpectedCheckIn());
            booking.setExpectedCheckOut(request.getExpectedCheckOut());
            booking.setStatus(BookingStatus.PENDING.value());
            booking.setUser(booker);
            Booking bookingSaved = bookingStore.save(booking);

            Booking bookingResponse = bookingMapper.copy(bookingSaved);
            Set<BookingRoomDetail> bookingRoomDetails = this.createBookingRoomDetails(bookingSaved, roomsBooking);
            bookingResponse.setBookingRoomDetails(bookingRoomDetails);

            if (CollectionUtils.isNotEmpty(request.getServices())) {
                Set<BookingServiceDetail> bookingServiceDetails =
                        this.createBookingServiceDetails(bookingSaved, request.getServices());
                bookingResponse.setBookingServiceDetails(bookingServiceDetails);
            }
            return this.toBookingResponse(bookingResponse);
        });
    }

    @Override
    @Transactional
    public CommonResponse cancelBooking(CancelBookingRequest request, UserPrincipal principal) {
        Booking booking = bookingStore.get(request.getBookingId());
        if (ObjectUtils.isEmpty(booking)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.BOOKING_NOT_FOUND);
        }
        if (!booking.getUser().getId().equals(principal.getUser().getId())) {
            throw new BaseResponseException(ResponseStatusCodeEnum.NOT_PERMISSION_DELETE_UPDATE);
        }
        LocalDateTime now = LocalDateTime.now();
        long totalDays = TimeUtils.getDaysBetween(booking.getExpectedCheckIn(), now);
        if (totalDays >= 1) {
            throw new BaseResponseException(ResponseStatusCodeEnum.CAN_NOT_CANCEL_BOOKING);
        }
        booking.setNote(request.getNote());
        booking.setStatus(BookingStatus.CANCEL.value());
        bookingStore.save(booking);
        this.notificationCancelBooking(booking);
        return new CommonResponse(Boolean.TRUE, "Cancel booking successfully");
    }

}
