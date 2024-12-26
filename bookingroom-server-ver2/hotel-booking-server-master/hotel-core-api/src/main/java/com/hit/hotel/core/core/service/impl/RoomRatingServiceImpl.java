package com.hit.hotel.core.core.service.impl;

import com.hit.common.core.constants.CommonConstants;
import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.common.core.exception.BaseResponseException;
import com.hit.common.core.exception.ResponseStatusCodeEnum;
import com.hit.hotel.core.core.data.mapper.RoomRatingMapper;
import com.hit.hotel.core.core.data.request.RoomRatingCreateRequest;
import com.hit.hotel.core.core.data.request.RoomRatingUpdateRequest;
import com.hit.hotel.core.core.data.response.RoomRatingResponse;
import com.hit.hotel.core.core.service.RoomRatingService;
import com.hit.hotel.repository.role.constants.RoleConstants;
import com.hit.hotel.repository.room.RoomRatingStore;
import com.hit.hotel.repository.room.RoomStore;
import com.hit.hotel.repository.room.entity.Room;
import com.hit.hotel.repository.room.entity.RoomRating;
import com.hit.hotel.repository.user.UserStore;
import com.hit.hotel.repository.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomRatingServiceImpl implements RoomRatingService {

    private final RoomRatingStore roomRatingStore;

    private final RoomStore roomStore;

    private final UserStore userStore;

    private final RoomRatingMapper roomRatingMapper;

    @Override
    public PaginationResponse<RoomRatingResponse> getRoomRatings(PaginationRequest paginationRequest,
                                                                 Integer roomId, Integer star) {
        return roomRatingStore.getRoomRatingByRoomIdAndStar(paginationRequest, roomId, star)
                .map(roomRatingMapper::toRoomRatingResponse);
    }

    @Override
    public RoomRatingResponse createRoomRating(Integer roomId, RoomRatingCreateRequest request,
                                               UserPrincipal userPrincipal) {
        Room room = roomStore.get(roomId);
        if (ObjectUtils.isEmpty(room)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.ROOM_NOT_FOUND);
        }
        User creator = userStore.getUserByEmail(userPrincipal.getUser().getEmail());
        RoomRating roomRating = roomRatingMapper.toRoomRating(request);
        roomRating.setRoom(room);
        roomRating.setUser(creator);
        return roomRatingMapper.toRoomRatingResponse(roomRatingStore.save(roomRating), creator);
    }

    @Override
    public RoomRatingResponse updateRoomRating(Integer roomRatingId, RoomRatingUpdateRequest request,
                                               UserPrincipal userPrincipal) {
        RoomRating roomRating = roomRatingStore.get(roomRatingId);
        if (ObjectUtils.isEmpty(roomRating)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.ROOM_RATING_NOT_FOUND);
        }
        if (roomRating.getUser() == null || !userPrincipal.getUser().getId().equals(roomRating.getUser().getId())) {
            throw new BaseResponseException(ResponseStatusCodeEnum.NOT_PERMISSION_UPDATE_RATING);
        }
        roomRatingMapper.updateRoomRating(roomRating, request);
        return roomRatingMapper.toRoomRatingResponse(roomRatingStore.save(roomRating), roomRating.getUser());
    }

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
        roomRatingStore.save(roomRating);
        return new CommonResponse(Boolean.TRUE, CommonConstants.DELETE_SUCCESS);
    }


}
