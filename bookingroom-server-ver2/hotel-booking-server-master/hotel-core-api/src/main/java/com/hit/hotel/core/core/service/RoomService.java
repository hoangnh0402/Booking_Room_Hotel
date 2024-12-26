package com.hit.hotel.core.core.service;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.hotel.core.core.data.response.RoomDetailResponse;
import com.hit.hotel.core.core.data.response.RoomResponse;
import com.hit.hotel.common.domain.request.RoomFilterRequest;

public interface RoomService {

    PaginationResponse<RoomResponse> getRooms(PaginationRequest paginationRequest, RoomFilterRequest filterRequest);

    RoomDetailResponse getRoomDetail(Integer roomId);

}
