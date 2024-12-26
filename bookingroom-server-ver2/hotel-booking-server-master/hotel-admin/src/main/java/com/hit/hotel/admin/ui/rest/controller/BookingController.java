package com.hit.hotel.admin.ui.rest.controller;

import com.hit.api.factory.GeneralResponse;
import com.hit.api.factory.ResponseFactory;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.admin.core.data.request.booking.BookingFilterRequest;
import com.hit.hotel.admin.core.data.request.booking.BookingServiceRequest;
import com.hit.hotel.admin.core.service.BookingService;
import com.hit.hotel.admin.ui.rest.BookingOperations;
import com.hit.hotel.common.domain.response.booking.BookingDetailResponse;
import com.hit.hotel.common.domain.response.booking.BookingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("bookingControllerAdmin")
public class BookingController implements BookingOperations {

    private final BookingService bookingService;

    public ResponseEntity<GeneralResponse<PaginationResponse<BookingResponse>>> getBookings(
            PaginationRequest paginationRequest, BookingFilterRequest filterRequest) {
        return ResponseFactory.success(bookingService.getBookings(paginationRequest, filterRequest));
    }

    @Override
    public ResponseEntity<GeneralResponse<BookingDetailResponse>> getBookingDetail(Integer bookingId) {
        return ResponseFactory.success(bookingService.getBookingDetail(bookingId));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> addService(Integer bookingId,
                                                                      BookingServiceRequest request) {
        return ResponseFactory.success(bookingService.addServiceBooking(bookingId, request));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> checkIn(Integer bookingId) {
        return ResponseFactory.success(bookingService.checkIn(bookingId));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> checkOut(Integer bookingId) {
        return ResponseFactory.success(bookingService.checkOut(bookingId));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> cancel(Integer bookingId, String note) {
        return ResponseFactory.success(bookingService.cancelBooking(bookingId, note));
    }
}
