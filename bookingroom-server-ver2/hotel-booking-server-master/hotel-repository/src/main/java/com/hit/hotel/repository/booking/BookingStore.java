package com.hit.hotel.repository.booking;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.hotel.repository.booking.entity.Booking;
import com.hit.hotel.repository.booking.model.condition.BookingFilterCondition;
import com.hit.hotel.repository.booking.model.condition.StatisticRevenueCondition;
import com.hit.hotel.repository.booking.model.projection.StatisticBookingStatusProjection;
import com.hit.hotel.repository.booking.model.projection.StatisticCustomerTopBookingProjection;

import java.util.List;

public interface BookingStore {

    Booking get(Integer id);

    Booking save(Booking booking);

    Booking getDetail(Integer id);

    PaginationResponse<Booking> getBookingsByBookerId(PaginationRequest paginationRequest, String bookerId);

    PaginationResponse<Booking> getBookings(PaginationRequest paginationRequest, BookingFilterCondition condition);

    List<StatisticCustomerTopBookingProjection> getCustomersTopBooking();

    List<Booking> statisticRevenue(StatisticRevenueCondition condition);

    List<StatisticBookingStatusProjection> statisticBookingByStatusIn(Integer month, Integer year, List<String> status);

}
