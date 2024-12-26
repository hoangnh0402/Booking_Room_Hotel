package com.hit.hotel.common.domain.request;

import com.hit.hotel.common.service.AbsBookingService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomFilterRequest {

    @Parameter(description = "checkin format yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkIn;

    @Parameter(description = "checkout format yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkOut;

    private String roomType;

    private Integer capacity;

    private Boolean deleteFlag = Boolean.FALSE;

    public LocalDateTime getCheckIn() {
        return checkIn == null ? LocalDateTime.of(LocalDate.now(), AbsBookingService.TIME_14H00) :
                LocalDateTime.of(checkIn, AbsBookingService.TIME_14H00);
    }

    public LocalDateTime getCheckOut() {
        return checkOut == null ?
                LocalDateTime.of(LocalDate.now().plusDays(1), AbsBookingService.TIME_12H00) :
                LocalDateTime.of(checkOut, AbsBookingService.TIME_14H00);
    }

}
