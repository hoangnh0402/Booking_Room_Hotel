package com.hit.hotel.admin.ui.rest;

import com.hit.api.factory.GeneralResponse;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.hotel.admin.core.data.request.StatisticRevenueRequest;
import com.hit.hotel.admin.core.data.response.StatisticBookingResponse;
import com.hit.hotel.admin.core.data.response.StatisticRevenueResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Validated
@RequestMapping("${app.admin-api-path-prefix}/api/v1/statistic")
public interface StatisticOperations {

    @Operation(summary = "API statistic room booked by month and year")
    @GetMapping("/room-booked-month")
    ResponseEntity<GeneralResponse<PaginationResponse<Map<String, Object>>>> statisticRoomBookedForMonth(
            @Valid @ParameterObject PaginationRequest paginationRequest,
            @RequestParam Integer month, @RequestParam Integer year);

    @Operation(summary = "API statistic customer top booking")
    @GetMapping("/top-booking")
    ResponseEntity<GeneralResponse<List<Map<String, Object>>>> statisticCustomerTopBooking();

    @Operation(summary = "API statistic revenue by month and year")
    @GetMapping("/revenue")
    ResponseEntity<GeneralResponse<List<StatisticRevenueResponse>>> statisticRevenue(
            @Valid @ParameterObject StatisticRevenueRequest request);

    @Operation(summary = "API statistic booking by status")
    @GetMapping("/booking-status")
    ResponseEntity<GeneralResponse<StatisticBookingResponse>> statisticBookingForMonth(@RequestParam Integer month,
                                                                                       @RequestParam Integer year);

}
