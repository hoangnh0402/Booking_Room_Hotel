package com.hit.hotel.core.ui.rest.controller;

import com.hit.api.factory.GeneralResponse;
import com.hit.api.factory.ResponseFactory;
import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.core.core.data.request.RoomRatingCreateRequest;
import com.hit.hotel.core.core.data.request.RoomRatingUpdateRequest;
import com.hit.hotel.core.core.data.response.RoomRatingResponse;
import com.hit.hotel.core.core.service.RoomRatingService;
import com.hit.hotel.core.ui.rest.RoomRatingOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomRatingController implements RoomRatingOperations {

    private final RoomRatingService roomRatingService;

    @Override
    public ResponseEntity<GeneralResponse<PaginationResponse<RoomRatingResponse>>> getRoomRatingsByRoom(
            PaginationRequest request, Integer roomId, Integer star) {
        return ResponseFactory.success(roomRatingService.getRoomRatings(request, roomId, star));
    }

    @Override
    public ResponseEntity<GeneralResponse<RoomRatingResponse>> createRoomRating(Integer roomId,
                                                                                RoomRatingCreateRequest request,
                                                                                UserPrincipal userPrincipal) {
        return ResponseFactory.success(roomRatingService.createRoomRating(roomId, request, userPrincipal));
    }

    @Override
    public ResponseEntity<GeneralResponse<RoomRatingResponse>> updateRoomRating(Integer roomRatingId,
                                                                                RoomRatingUpdateRequest request,
                                                                                UserPrincipal userPrincipal) {
        return ResponseFactory.success(roomRatingService.updateRoomRating(roomRatingId, request, userPrincipal));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> deleteRoomRating(Integer roomRatingId,
                                                                            UserPrincipal userPrincipal) {
        return ResponseFactory.success(roomRatingService.deleteRoomRating(roomRatingId, userPrincipal));
    }
}
