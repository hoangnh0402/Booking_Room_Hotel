package com.hit.hotel.admin.core.service;


import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.hotel.admin.core.data.request.StatisticRevenueRequest;
import com.hit.hotel.admin.core.data.response.StatisticBookingResponse;
import com.hit.hotel.admin.core.data.response.StatisticRevenueResponse;

import java.util.List;
import java.util.Map;

public interface StatisticService {

    PaginationResponse<Map<String, Object>> statisticRoomBooked(PaginationRequest request,
                                                                Integer month, Integer year);

    List<Map<String, Object>> statisticCustomerTopBooking();

    List<StatisticRevenueResponse> statisticRevenue(StatisticRevenueRequest request);

    StatisticBookingResponse statisticBooking(Integer month, Integer year);

}
