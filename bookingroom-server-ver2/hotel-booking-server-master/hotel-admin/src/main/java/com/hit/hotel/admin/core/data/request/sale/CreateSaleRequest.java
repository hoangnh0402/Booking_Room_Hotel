package com.hit.hotel.admin.core.data.request.sale;

import com.hit.common.annotation.ValidDateTime;
import com.hit.common.core.constants.ErrorMessage;
import com.hit.common.utils.TimeUtils;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSaleRequest {

    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @ValidDateTime(pattern = TimeUtils.DATE_TIME_PATTERN,
            message = "Invalid format datetime, format: " + TimeUtils.DATE_TIME_PATTERN)
    private String dayStart;

    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @ValidDateTime(pattern = TimeUtils.DATE_TIME_PATTERN,
            message = "Invalid format datetime, format: " + TimeUtils.DATE_TIME_PATTERN)
    private String dayEnd;

    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    private Float salePercent;

    public LocalDateTime getDayStart() {
        return TimeUtils.parseToLocalDateTime(dayStart, TimeUtils.DATE_TIME_PATTERN);
    }

    public LocalDateTime getDayEnd() {
        return TimeUtils.parseToLocalDateTime(dayEnd, TimeUtils.DATE_TIME_PATTERN);
    }


}
