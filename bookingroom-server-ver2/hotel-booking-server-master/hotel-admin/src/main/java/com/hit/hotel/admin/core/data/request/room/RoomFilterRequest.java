package com.hit.hotel.admin.core.data.request.room;

import com.hit.hotel.admin.core.constants.Constants;
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
@NoArgsConstructor
@AllArgsConstructor
public class RoomFilterRequest {

    @Parameter(description = "checkin format yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkIn;

    @Parameter(description = "checkout format yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkOut;

    private String roomType;

    private Integer capacity;

    public LocalDateTime getCheckIn() {
        return checkIn == null ? LocalDateTime.of(LocalDate.now(), Constants.TIME_14H00) :
                LocalDateTime.of(checkIn, Constants.TIME_14H00);
    }

    public LocalDateTime getCheckOut() {
        return checkOut == null ? LocalDateTime.of(LocalDate.now().plusDays(1), Constants.TIME_12H00) :
                LocalDateTime.of(checkOut, Constants.TIME_14H00);
    }

}
