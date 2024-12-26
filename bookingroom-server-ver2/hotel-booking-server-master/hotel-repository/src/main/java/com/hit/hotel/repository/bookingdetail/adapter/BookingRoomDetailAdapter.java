package com.hit.hotel.repository.bookingdetail.adapter;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.utils.PaginationUtils;
import com.hit.hotel.repository.bookingdetail.BookingRoomDetailStore;
import com.hit.hotel.repository.bookingdetail.entity.BookingRoomDetail;
import com.hit.hotel.repository.bookingdetail.model.StatisticRoomBookedProjection;
import com.hit.hotel.repository.bookingdetail.repository.BookingRoomDetailRepository;
import com.hit.jpa.BaseJPAAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class BookingRoomDetailAdapter extends BaseJPAAdapter<BookingRoomDetail, Integer, BookingRoomDetailRepository>
        implements BookingRoomDetailStore {

    @Override
    protected Class<BookingRoomDetail> getEntityClass() {
        return BookingRoomDetail.class;
    }

    @Override
    public Set<BookingRoomDetail> getAllByBookingId(Integer bookingId) {
        return this.repository.getAllByBookingId(bookingId);
    }

    @Override
    public PaginationResponse<StatisticRoomBookedProjection> statisticRoomBooked(PaginationRequest request,
                                                                                 Integer month, Integer year) {
        Pageable pageable = PaginationUtils.buildPageable(request);
        Page<StatisticRoomBookedProjection> statisticRoomBooked =
                this.repository.statisticRoomBooked(month, year, request.getKeyword(), request.getSortType(), pageable);
        return new PaginationResponse<>(request, statisticRoomBooked);
    }
}
