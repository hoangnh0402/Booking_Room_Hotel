package com.hit.hotel.core.ui.rest.controller;

import com.hit.api.factory.GeneralResponse;
import com.hit.api.factory.ResponseFactory;
import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.core.core.data.request.CancelBookingRequest;
import com.hit.hotel.core.core.service.BookingService;
import com.hit.hotel.core.ui.rest.BookingOperations;
import com.hit.hotel.common.domain.request.BookingRequest;
import com.hit.hotel.common.domain.response.booking.BookingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookingController implements BookingOperations {

    private final BookingService bookingService;

    @Override
    public ResponseEntity<GeneralResponse<PaginationResponse<BookingResponse>>>
    getBookings(PaginationRequest paginationRequest, UserPrincipal userPrincipal) {
        return ResponseFactory.success(bookingService.getBookings(paginationRequest, userPrincipal));
    }

    @Override
    public ResponseEntity<GeneralResponse<BookingResponse>> booking(BookingRequest request,
                                                                    UserPrincipal userPrincipal) {
        return ResponseFactory.success(bookingService.booking(request, userPrincipal));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> cancelBooking(CancelBookingRequest request,
                                                                         UserPrincipal userPrincipal) {
        return ResponseFactory.success(bookingService.cancelBooking(request, userPrincipal));
    }
}
