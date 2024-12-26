package com.hit.hotel.core.core.service;

import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.core.core.data.request.RoomRatingCreateRequest;
import com.hit.hotel.core.core.data.request.RoomRatingUpdateRequest;
import com.hit.hotel.core.core.data.response.RoomRatingResponse;

public interface RoomRatingService {

    PaginationResponse<RoomRatingResponse> getRoomRatings(PaginationRequest paginationRequest,
                                                          Integer roomId, Integer star);

    RoomRatingResponse createRoomRating(Integer roomId, RoomRatingCreateRequest request, UserPrincipal userPrincipal);

    RoomRatingResponse updateRoomRating(Integer roomRatingId, RoomRatingUpdateRequest request, UserPrincipal userPrincipal);

    CommonResponse deleteRoomRating(Integer roomRatingId, UserPrincipal userPrincipal);

}
