package com.hit.hotel.repository.booking.model.condition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticRevenueCondition {

    private Integer fromMonth;

    private Integer toMonth;

    private Integer year;

}
