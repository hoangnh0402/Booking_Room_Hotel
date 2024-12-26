package com.hit.hotel.common.domain.request;

import com.hit.common.annotation.ValidDateTime;
import com.hit.common.core.constants.ErrorMessage;
import com.hit.common.utils.TimeUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {

    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    private List<Integer> roomIds;

    @Valid
    private List<BookingServiceRequest> services;

    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @ValidDateTime(pattern = TimeUtils.DATE_TIME_PATTERN,
            message = "Invalid format datetime, format: " + TimeUtils.DATE_TIME_PATTERN)
    private String expectedCheckIn;

    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @ValidDateTime(pattern = TimeUtils.DATE_TIME_PATTERN,
            message = "Invalid format datetime, format: " + TimeUtils.DATE_TIME_PATTERN)
    private String expectedCheckOut;

    public LocalDateTime getExpectedCheckIn() {
        return TimeUtils.parseToLocalDateTime(expectedCheckIn, TimeUtils.DATE_TIME_PATTERN);
    }

    public LocalDateTime getExpectedCheckOut() {
        return TimeUtils.parseToLocalDateTime(expectedCheckOut, TimeUtils.DATE_TIME_PATTERN);
    }
}
