package com.hit.hotel.admin.core.data.request.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingFilterRequest {

    private LocalDate checkin;

    private LocalDate checkout;

    private String bookingStatus;

}
