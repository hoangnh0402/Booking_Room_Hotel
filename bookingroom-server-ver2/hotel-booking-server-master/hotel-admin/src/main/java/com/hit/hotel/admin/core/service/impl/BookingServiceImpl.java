package com.hit.hotel.admin.core.service.impl;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.common.core.exception.BaseResponseException;
import com.hit.common.core.exception.ResponseStatusCodeEnum;
import com.hit.common.core.json.JsonMapper;
import com.hit.hotel.admin.core.data.request.booking.BookingFilterRequest;
import com.hit.hotel.admin.core.data.request.booking.BookingServiceRequest;
import com.hit.hotel.admin.core.service.BookingService;
import com.hit.hotel.repository.booking.constants.BookingStatus;
import com.hit.hotel.repository.booking.entity.Booking;
import com.hit.hotel.repository.booking.model.condition.BookingFilterCondition;
import com.hit.hotel.repository.bookingdetail.entity.BookingServiceDetail;
import com.hit.hotel.repository.service.entity.Service;
import com.hit.hotel.common.domain.response.booking.BookingDetailResponse;
import com.hit.hotel.common.domain.response.booking.BookingResponse;
import com.hit.hotel.common.service.AbsBookingService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@org.springframework.stereotype.Service("bookingServiceAdmin")
public class BookingServiceImpl extends AbsBookingService implements BookingService {

    @Override
    public PaginationResponse<BookingResponse> getBookings(PaginationRequest paginationRequest,
                                                           BookingFilterRequest filterRequest) {
        BookingFilterCondition condition = JsonMapper.getObjectMapper().convertValue(filterRequest, BookingFilterCondition.class);
        return bookingStore.getBookings(paginationRequest, condition).map(this::toBookingResponse);
    }

    @Override
    public BookingDetailResponse getBookingDetail(Integer id) {
        Booking booking = bookingStore.getDetail(id);
        return this.toBookingDetailResponse(booking);
    }

    @Override
    @Transactional
    public CommonResponse addServiceBooking(Integer bookingId, BookingServiceRequest request) {
        Booking booking = bookingStore.get(bookingId);
        if (ObjectUtils.isEmpty(booking)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.BOOKING_NOT_FOUND);
        }
        this.createBookingServiceDetail(booking, request);
        return new CommonResponse(Boolean.TRUE, "Add Service Successfully");
    }

    @Override
    public CommonResponse checkIn(Integer bookingId) {
        Booking booking = bookingStore.get(bookingId);
        if (ObjectUtils.isEmpty(booking)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.BOOKING_NOT_FOUND);
        }
        LocalDateTime now = LocalDateTime.now();
        booking.setCheckIn(now);
        booking.setStatus(BookingStatus.CHECKED_IN.value());
        bookingStore.save(booking);
        return new CommonResponse(Boolean.TRUE, "Check-in Successfully");
    }

    @Override
    public CommonResponse checkOut(Integer bookingId) {
        Booking booking = bookingStore.get(bookingId);
        if (ObjectUtils.isEmpty(booking)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.BOOKING_NOT_FOUND);
        }
        LocalDateTime now = LocalDateTime.now();
        booking.setCheckOut(now);
        booking.setStatus(BookingStatus.CHECKED_OUT.value());
        bookingStore.save(booking);
        return new CommonResponse(Boolean.TRUE, "Check-in Successfully");
    }

    @Override
    public CommonResponse cancelBooking(Integer bookingId, String note) {
        Booking booking = bookingStore.get(bookingId);
        if (ObjectUtils.isEmpty(booking)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.BOOKING_NOT_FOUND);
        }
        booking.setNote(note);
        booking.setStatus(BookingStatus.CANCEL.value());
        bookingStore.save(booking);
        return new CommonResponse(Boolean.TRUE, "Cancel Booking Successfully");
    }

    protected void createBookingServiceDetail(Booking booking, BookingServiceRequest request) {
        Service service = serviceStore.get(request.getServiceId());
        if (ObjectUtils.isEmpty(service)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.SERVICE_NOT_FOUND);
        }
        if (ObjectUtils.isEmpty(service.getPrice()) && ObjectUtils.isEmpty(request.getPrice())) {
            throw new BaseResponseException(ResponseStatusCodeEnum.VALIDATION_ERROR);
        }
        BookingServiceDetail serviceDetail = new BookingServiceDetail();
        serviceDetail.setPrice(ObjectUtils.isEmpty(service.getPrice()) ? request.getPrice() : service.getPrice());
        serviceDetail.setQuantity(request.getQuantity());
        serviceDetail.setBooking(booking);
        serviceDetail.setService(service);
        bookingServiceDetailStore.save(serviceDetail);
    }
}
