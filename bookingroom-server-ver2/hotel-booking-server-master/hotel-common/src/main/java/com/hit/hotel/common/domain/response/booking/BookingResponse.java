package com.hit.hotel.common.domain.response.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hit.common.core.domain.dto.DateAuditingDTO;
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
public class BookingResponse extends DateAuditingDTO {

    private Integer id;

    @JsonFormat(pattern = TimeUtils.DATE_TIME_PATTERN)
    private LocalDateTime expectedCheckIn;

    @JsonFormat(pattern = TimeUtils.DATE_TIME_PATTERN)
    private LocalDateTime expectedCheckOut;

    @JsonFormat(pattern = TimeUtils.DATE_TIME_PATTERN)
    private LocalDateTime checkIn;

    @JsonFormat(pattern = TimeUtils.DATE_TIME_PATTERN)
    private LocalDateTime checkOut;

    private String status;

    private String note;

    private Double totalRoomPrice;

    private Double totalServicePrice;

    private Long totalSurcharge;

    private BookerResponse booker;

}
