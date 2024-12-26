package com.hit.hotel.core.ui.rest;

import com.hit.api.config.annotation.UserPrincipalRequest;
import com.hit.api.factory.GeneralResponse;
import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.common.domain.request.BookingRequest;
import com.hit.hotel.common.domain.response.booking.BookingResponse;
import com.hit.hotel.core.core.data.request.CancelBookingRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@RequestMapping("${app.core-api-path-prefix}/api/v1/booking")
public interface BookingOperations {

    @Operation(summary = "API booking")
    @GetMapping
    ResponseEntity<GeneralResponse<PaginationResponse<BookingResponse>>> getBookings(
            @Valid @ParameterObject PaginationRequest paginationRequest,
            @Parameter(name = "userPrincipal", hidden = true) @UserPrincipalRequest UserPrincipal userPrincipal);

    @Operation(summary = "API booking")
    @PostMapping
    ResponseEntity<GeneralResponse<BookingResponse>> booking(
            @Valid @RequestBody BookingRequest request,
            @Parameter(name = "userPrincipal", hidden = true) @UserPrincipalRequest UserPrincipal userPrincipal);

    @Operation(summary = "API cancel booking")
    @PostMapping("/cancel")
    ResponseEntity<GeneralResponse<CommonResponse>> cancelBooking(
            @Valid @RequestBody CancelBookingRequest request,
            @Parameter(name = "userPrincipal", hidden = true) @UserPrincipalRequest UserPrincipal userPrincipal);

}
