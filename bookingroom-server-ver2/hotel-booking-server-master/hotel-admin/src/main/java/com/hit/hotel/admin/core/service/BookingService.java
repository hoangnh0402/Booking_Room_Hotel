package com.hit.hotel.admin.core.service;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.admin.core.data.request.booking.BookingFilterRequest;
import com.hit.hotel.admin.core.data.request.booking.BookingServiceRequest;
import com.hit.hotel.common.domain.response.booking.BookingDetailResponse;
import com.hit.hotel.common.domain.response.booking.BookingResponse;

public interface BookingService {

    PaginationResponse<BookingResponse> getBookings(PaginationRequest paginationRequest,
                                                    BookingFilterRequest filterRequest);

    BookingDetailResponse getBookingDetail(Integer id);

    CommonResponse addServiceBooking(Integer bookingId, BookingServiceRequest request);

    CommonResponse checkIn(Integer bookingId);

    CommonResponse checkOut(Integer bookingId);

    CommonResponse cancelBooking(Integer bookingId, String note);

}
