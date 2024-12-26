package com.hit.hotel.repository.booking.constants;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public enum BookingStatus {

    PENDING("PENDING"),
    CHECKED_IN("CHECKED_IN"),
    CHECKED_OUT("CHECKED_OUT"),
    CANCEL("CANCEL");

    private final String value;

    public String value() {
        return this.value;
    }

    public static List<String> getAll() {
        return Arrays.stream(BookingStatus.values()).map(BookingStatus::value).toList();
    }
}
