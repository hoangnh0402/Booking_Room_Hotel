package com.hit.hotel.repository.bookingdetail.entity;

import com.hit.hotel.repository.base.UserDateAuditing;
import com.hit.hotel.repository.booking.entity.Booking;
import com.hit.hotel.repository.room.entity.Room;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking_room_detail")
@Entity(name = "BookingRoomDetail")
public class BookingRoomDetail extends UserDateAuditing {

    @EmbeddedId
    BookingRoomDetailId id;

    //Link to table Booking
    @MapsId("bookingId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", foreignKey = @ForeignKey(name = "fk_booking_room_detail__booking_id"))
    private Booking booking;

    //Link to table Room
    @MapsId("roomId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", foreignKey = @ForeignKey(name = "fk_booking_room_detail__room_id"))
    private Room room;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "sale_percent")
    private Float salePercent;

    public BookingRoomDetail(Booking booking, Room room) {
        this.id = new BookingRoomDetailId(booking.getId(), room.getId());
        this.booking = booking;
        this.room = room;
    }

}