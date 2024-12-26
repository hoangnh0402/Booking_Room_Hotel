package com.hit.hotel.repository.room.model.condition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RoomFilterCondition {

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    private String roomType;

    private Integer capacity;

    private Boolean deleteFlag;

}
