package com.hit.hotel.repository.bookingdetail.entity;

import com.hit.hotel.repository.base.UserDateAuditing;
import com.hit.hotel.repository.booking.entity.Booking;
import com.hit.hotel.repository.service.entity.Service;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking_service_detail")
@Entity(name = "BookingServiceDetail")
public class BookingServiceDetail extends UserDateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    //Link to table Booking
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", foreignKey = @ForeignKey(name = "fk_booking_service_detail__booking_id"))
    private Booking booking;

    //Link to table Service
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", foreignKey = @ForeignKey(name = "fk_booking_service_detail__service_id"))
    private Service service;

}
