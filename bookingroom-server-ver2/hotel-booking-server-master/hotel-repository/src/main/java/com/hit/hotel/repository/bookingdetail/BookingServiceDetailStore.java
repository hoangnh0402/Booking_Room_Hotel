package com.hit.hotel.repository.bookingdetail;

import com.hit.hotel.repository.bookingdetail.entity.BookingServiceDetail;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface BookingServiceDetailStore {

    BookingServiceDetail save(BookingServiceDetail bookingServiceDetail);

    List<BookingServiceDetail> saveAll(Collection<BookingServiceDetail> bookingServiceDetails);

    Set<BookingServiceDetail> getAllByBookingId(Integer bookingId);

}
