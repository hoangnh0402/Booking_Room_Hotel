package com.hit.hotel.repository.bookingdetail.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BookingRoomDetailId implements Serializable {

    @Column(name = "booking_id")
    private Integer bookingId;

    @Column(name = "room_id")
    private Integer roomId;

}
