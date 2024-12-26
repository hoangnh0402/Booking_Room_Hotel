package com.hit.hotel.repository.booking.model.condition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingFilterCondition {

    private LocalDate checkin;

    private LocalDate checkout;

    private String bookingStatus;

    public String getBookingStatus() {
        return StringUtils.isEmpty(bookingStatus) ? null : bookingStatus;
    }
}
