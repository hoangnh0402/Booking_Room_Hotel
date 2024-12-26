package com.hit.hotel.admin.core.data.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticRevenueResponse {
    private String month;
    private Integer totalBooking;
    private Long totalRevenue;
}