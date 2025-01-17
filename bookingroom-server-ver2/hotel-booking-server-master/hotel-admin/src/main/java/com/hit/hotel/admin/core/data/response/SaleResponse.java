package com.hit.hotel.admin.core.data.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hit.common.core.domain.dto.CreatorDTO;
import com.hit.common.core.domain.dto.DateAuditingDTO;
import com.hit.common.core.domain.dto.ModifierDTO;
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
public class SaleResponse extends DateAuditingDTO {

    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TimeUtils.DATE_TIME_PATTERN)
    private LocalDateTime dayStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TimeUtils.DATE_TIME_PATTERN)
    private LocalDateTime dayEnd;

    private Float salePercent;

    private CreatorDTO creator;

    private ModifierDTO modifier;

}
