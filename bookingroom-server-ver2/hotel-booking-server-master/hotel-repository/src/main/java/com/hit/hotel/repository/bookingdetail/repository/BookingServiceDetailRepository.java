package com.hit.hotel.repository.bookingdetail.repository;

import com.hit.hotel.repository.bookingdetail.entity.BookingServiceDetail;
import com.hit.jpa.BaseJPARepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface BookingServiceDetailRepository extends BaseJPARepository<BookingServiceDetail, Integer> {

    @Query("SELECT bsd FROM BookingServiceDetail bsd WHERE bsd.booking.id = ?1")
    Set<BookingServiceDetail> getAllByBookingId(Integer id);

}
