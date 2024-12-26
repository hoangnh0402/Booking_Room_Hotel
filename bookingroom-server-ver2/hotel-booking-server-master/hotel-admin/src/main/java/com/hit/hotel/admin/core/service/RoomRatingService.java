package com.hit.hotel.admin.core.service;

import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.response.CommonResponse;

public interface RoomRatingService {

    CommonResponse deleteRoomRating(Integer roomRatingId, UserPrincipal userPrincipal);

}
