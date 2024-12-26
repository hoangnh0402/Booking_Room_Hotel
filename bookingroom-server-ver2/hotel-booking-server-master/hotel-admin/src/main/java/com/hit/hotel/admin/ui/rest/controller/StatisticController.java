package com.hit.hotel.admin.ui.rest.controller;

import com.hit.api.factory.GeneralResponse;
import com.hit.api.factory.ResponseFactory;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.hotel.admin.core.data.request.StatisticRevenueRequest;
import com.hit.hotel.admin.core.data.response.StatisticBookingResponse;
import com.hit.hotel.admin.core.data.response.StatisticRevenueResponse;
import com.hit.hotel.admin.core.service.StatisticService;
import com.hit.hotel.admin.ui.rest.StatisticOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController("statisticControllerAdmin")
public class StatisticController implements StatisticOperations {

    private final StatisticService statisticService;

    @Override
    public ResponseEntity<GeneralResponse<PaginationResponse<Map<String, Object>>>> statisticRoomBookedForMonth(
            PaginationRequest paginationRequest,
            Integer month, Integer year) {
        return ResponseFactory.success(statisticService.statisticRoomBooked(paginationRequest, month, year));
    }

    @Override
    public ResponseEntity<GeneralResponse<List<Map<String, Object>>>> statisticCustomerTopBooking() {
        return ResponseFactory.success(statisticService.statisticCustomerTopBooking());
    }

    @Override
    public ResponseEntity<GeneralResponse<List<StatisticRevenueResponse>>> statisticRevenue(StatisticRevenueRequest request) {
        return ResponseFactory.success(statisticService.statisticRevenue(request));
    }

    @Override
    public ResponseEntity<GeneralResponse<StatisticBookingResponse>> statisticBookingForMonth(Integer month,
                                                                                              Integer year) {
        return ResponseFactory.success(statisticService.statisticBooking(month, year));
    }
}
