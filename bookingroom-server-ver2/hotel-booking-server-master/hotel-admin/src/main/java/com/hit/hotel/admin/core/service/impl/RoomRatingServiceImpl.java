package com.hit.hotel.admin.core.service.impl;

import com.hit.common.core.constants.CommonConstants;
import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.common.core.exception.BaseResponseException;
import com.hit.common.core.exception.ResponseStatusCodeEnum;
import com.hit.hotel.admin.core.service.RoomRatingService;
import com.hit.hotel.repository.role.constants.RoleConstants;
import com.hit.hotel.repository.room.RoomRatingStore;
import com.hit.hotel.repository.room.entity.RoomRating;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("roomRatingServiceAdmin")
public class RoomRatingServiceImpl implements RoomRatingService {

    private final RoomRatingStore roomRatingStore;

    @Override
    public CommonResponse deleteRoomRating(Integer roomRatingId, UserPrincipal userPrincipal) {
        RoomRating roomRating = roomRatingStore.get(roomRatingId);
        if (ObjectUtils.isEmpty(roomRating)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.ROOM_RATING_NOT_FOUND);
        }
        if (roomRating.getUser() == null || !userPrincipal.getUser().getId().equals(roomRating.getUser().getId())
                || !RoleConstants.isAdmin(userPrincipal)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.NOT_PERMISSION_DELETE_RATING);
        }
        roomRating.setDeleteFlag(Boolean.TRUE);
        roomRatingStore.update(roomRating);
        return new CommonResponse(Boolean.TRUE, CommonConstants.DELETE_SUCCESS);
    }


}
