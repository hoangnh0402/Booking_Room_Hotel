package com.hit.hotel.admin.ui.rest.controller;

import com.hit.api.factory.GeneralResponse;
import com.hit.api.factory.ResponseFactory;
import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.admin.core.service.RoomRatingService;
import com.hit.hotel.admin.ui.rest.RoomRatingOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("roomRatingControllerAdmin")
public class RoomRatingController implements RoomRatingOperations {

    private final RoomRatingService roomRatingService;

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> deleteRoomRating(Integer roomRatingId,
                                                                            UserPrincipal userPrincipal) {
        return ResponseFactory.success(roomRatingService.deleteRoomRating(roomRatingId, userPrincipal));
    }
}
