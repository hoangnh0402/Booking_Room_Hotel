package com.hit.hotel.admin.ui.rest;

import com.hit.api.factory.GeneralResponse;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.admin.core.data.request.booking.BookingFilterRequest;
import com.hit.hotel.admin.core.data.request.booking.BookingServiceRequest;
import com.hit.hotel.common.domain.response.booking.BookingDetailResponse;
import com.hit.hotel.common.domain.response.booking.BookingResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("${app.admin-api-path-prefix}/api/v1/booking")
public interface BookingOperations {

    @Operation(summary = "API get all booking")
    @GetMapping
    ResponseEntity<GeneralResponse<PaginationResponse<BookingResponse>>> getBookings(
            @Valid @ParameterObject PaginationRequest paginationRequest,
            @Valid @ParameterObject BookingFilterRequest filterRequest);

    @Operation(summary = "API get booking detail by id")
    @GetMapping("/{bookingId}")
    ResponseEntity<GeneralResponse<BookingDetailResponse>> getBookingDetail(@PathVariable Integer bookingId);

    @Operation(summary = "API add service for booking")
    @PostMapping("/{bookingId}/add-service")
    ResponseEntity<GeneralResponse<CommonResponse>> addService(@PathVariable Integer bookingId,
                                                               @RequestBody BookingServiceRequest request);

    @Operation(summary = "API checkin booking")
    @PostMapping("/check-in/{bookingId}")
    ResponseEntity<GeneralResponse<CommonResponse>> checkIn(@PathVariable Integer bookingId);

    @Operation(summary = "API checkout booking")
    @PostMapping("/check-out/{bookingId}")
    ResponseEntity<GeneralResponse<CommonResponse>> checkOut(@PathVariable Integer bookingId);

    @Operation(summary = "API checkout booking")
    @PostMapping("/cancel/{bookingId}")
    ResponseEntity<GeneralResponse<CommonResponse>> cancel(@PathVariable Integer bookingId, @RequestParam String note);

}
