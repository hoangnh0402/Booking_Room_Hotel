package com.hit.hotel.repository.booking.adapter;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.utils.PaginationUtils;
import com.hit.hotel.repository.booking.BookingStore;
import com.hit.hotel.repository.booking.entity.Booking;
import com.hit.hotel.repository.booking.model.condition.BookingFilterCondition;
import com.hit.hotel.repository.booking.model.condition.StatisticRevenueCondition;
import com.hit.hotel.repository.booking.model.projection.StatisticBookingStatusProjection;
import com.hit.hotel.repository.booking.model.projection.StatisticCustomerTopBookingProjection;
import com.hit.hotel.repository.booking.repository.BookingRepository;
import com.hit.jpa.BaseJPAAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingAdapter extends BaseJPAAdapter<Booking, Integer, BookingRepository> implements BookingStore {

    @Override
    protected Class<Booking> getEntityClass() {
        return Booking.class;
    }

    @Override
    public Booking getDetail(Integer id) {
        return this.repository.getBookingDetail(id);
    }

    @Override
    public PaginationResponse<Booking> getBookingsByBookerId(PaginationRequest paginationRequest, String bookerId) {
        Pageable pageable = PaginationUtils.buildPageableJPQL(paginationRequest);
        Page<Booking> bookings = this.repository.getBookingsByBookerId(bookerId, pageable);
        return new PaginationResponse<>(paginationRequest, bookings);
    }

    @Override
    public PaginationResponse<Booking> getBookings(PaginationRequest paginationRequest, BookingFilterCondition condition) {
        Pageable pageable = PaginationUtils.buildPageableJPQL(paginationRequest);
        Page<Booking> bookings = this.repository.getBookings(condition, pageable);
        return new PaginationResponse<>(paginationRequest, bookings);
    }

    @Override
    public List<StatisticCustomerTopBookingProjection> getCustomersTopBooking() {
        return this.repository.findAllCustomerTopBooking();
    }

    @Override
    public List<Booking> statisticRevenue(StatisticRevenueCondition condition) {
        return this.repository.statisticRevenue(condition);
    }

    @Override
    public List<StatisticBookingStatusProjection> statisticBookingByStatusIn(Integer month, Integer year,
                                                                             List<String> status) {
        return this.repository.statisticBookingByMonthAndYearAndStatusIn(month, year, status);
    }
}
