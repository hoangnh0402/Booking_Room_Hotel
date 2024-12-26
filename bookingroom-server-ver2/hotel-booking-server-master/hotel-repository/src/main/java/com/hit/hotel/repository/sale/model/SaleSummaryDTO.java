package com.hit.hotel.repository.sale.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hit.common.utils.TimeUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleSummaryDTO {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TimeUtils.DATE_TIME_PATTERN)
    private LocalDateTime dayStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TimeUtils.DATE_TIME_PATTERN)
    private LocalDateTime dayEnd;

    private Integer salePercent;

}
