package com.hit.hotel.repository.bookingdetail;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.hotel.repository.bookingdetail.entity.BookingRoomDetail;
import com.hit.hotel.repository.bookingdetail.model.StatisticRoomBookedProjection;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface BookingRoomDetailStore {

    List<BookingRoomDetail> saveAll(Collection<BookingRoomDetail> bookingRoomDetails);

    Set<BookingRoomDetail> getAllByBookingId(Integer bookingId);

    PaginationResponse<StatisticRoomBookedProjection> statisticRoomBooked(PaginationRequest request,
                                                                          Integer month, Integer year);

}
