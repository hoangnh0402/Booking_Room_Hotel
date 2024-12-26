package com.hit.hotel.repository.bookingdetail.adapter;

import com.hit.hotel.repository.bookingdetail.BookingServiceDetailStore;
import com.hit.hotel.repository.bookingdetail.entity.BookingServiceDetail;
import com.hit.hotel.repository.bookingdetail.repository.BookingServiceDetailRepository;
import com.hit.jpa.BaseJPAAdapter;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class BookingServiceDetailAdapter
        extends BaseJPAAdapter<BookingServiceDetail, Integer, BookingServiceDetailRepository>
        implements BookingServiceDetailStore {

    @Override
    protected Class<BookingServiceDetail> getEntityClass() {
        return BookingServiceDetail.class;
    }

    @Override
    public Set<BookingServiceDetail> getAllByBookingId(Integer bookingId) {
        return this.repository.getAllByBookingId(bookingId);
    }
}
