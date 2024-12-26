package com.hit.hotel.repository.booking.entity;


import com.hit.hotel.repository.base.UserDateAuditing;
import com.hit.hotel.repository.bookingdetail.entity.BookingRoomDetail;
import com.hit.hotel.repository.bookingdetail.entity.BookingServiceDetail;
import com.hit.hotel.repository.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking")
@Entity(name = "Booking")
public class Booking extends UserDateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "expected_check_in", nullable = false)
    private LocalDateTime expectedCheckIn;

    @Column(name = "expected_check_out", nullable = false)
    private LocalDateTime expectedCheckOut;

    @Column(name = "check_in")
    private LocalDateTime checkIn;

    @Column(name = "check_out")
    private LocalDateTime checkOut;

    @Column(name = "status", nullable = false)
    private String status;

    @Nationalized
    @Column(name = "note")
    private String note;

    //Link to table User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_booking__user_id"))
    private User user;

    //Link to table BookingRoomDetail
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "booking")
    private Set<BookingRoomDetail> bookingRoomDetails = new HashSet<>();

    //Link to table BookingServiceDetail
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "booking")
    private Set<BookingServiceDetail> bookingServiceDetails = new HashSet<>();

}

