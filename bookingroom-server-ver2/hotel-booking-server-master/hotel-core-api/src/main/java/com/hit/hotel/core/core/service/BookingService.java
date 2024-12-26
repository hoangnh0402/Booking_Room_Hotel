package com.hit.hotel.core.core.service;

import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.common.domain.request.BookingRequest;
import com.hit.hotel.common.domain.response.booking.BookingResponse;
import com.hit.hotel.core.core.data.request.CancelBookingRequest;

public interface BookingService {

    PaginationResponse<BookingResponse> getBookings(PaginationRequest paginationRequest, UserPrincipal principal);

    BookingResponse booking(BookingRequest request, UserPrincipal principal);

    CommonResponse cancelBooking(CancelBookingRequest request, UserPrincipal principal);

}
