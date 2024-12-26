package com.hit.common.core.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hit.common.utils.TimeUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public abstract class DateAuditingDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TimeUtils.DATE_TIME_PATTERN)
    private LocalDateTime createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TimeUtils.DATE_TIME_PATTERN)
    private LocalDateTime lastModifiedDate;

}
