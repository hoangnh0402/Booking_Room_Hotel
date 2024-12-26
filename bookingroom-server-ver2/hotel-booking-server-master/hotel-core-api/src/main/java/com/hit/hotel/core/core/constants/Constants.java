package com.hit.hotel.core.core.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final LocalTime TIME_5H00 = LocalTime.of(5, 0);
    public static final LocalTime TIME_9H00 = LocalTime.of(9, 0);
    public static final LocalTime TIME_14H00 = LocalTime.of(14, 0);

    public static final LocalTime TIME_12H00 = LocalTime.of(12, 0);
    public static final LocalTime TIME_15H00 = LocalTime.of(15, 0);
    public static final LocalTime TIME_18H00 = LocalTime.of(18, 0);

}
