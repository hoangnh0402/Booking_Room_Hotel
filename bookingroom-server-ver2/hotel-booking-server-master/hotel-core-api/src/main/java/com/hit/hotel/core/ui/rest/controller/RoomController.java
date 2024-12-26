package com.hit.hotel.core.ui.rest.controller;

import com.hit.api.factory.GeneralResponse;
import com.hit.api.factory.ResponseFactory;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.hotel.core.core.data.response.RoomDetailResponse;
import com.hit.hotel.core.core.data.response.RoomResponse;
import com.hit.hotel.core.core.service.RoomService;
import com.hit.hotel.core.ui.rest.RoomOperations;
import com.hit.hotel.common.domain.request.RoomFilterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomController implements RoomOperations {

    private final RoomService roomService;

    @Override
    public ResponseEntity<GeneralResponse<PaginationResponse<RoomResponse>>> getRooms(
            PaginationRequest paginationRequest, RoomFilterRequest filterRequest) {
        return ResponseFactory.success(roomService.getRooms(paginationRequest, filterRequest));
    }

    @Override
    public ResponseEntity<GeneralResponse<RoomDetailResponse>> getRoomDetail(Integer roomId) {
        return ResponseFactory.success(roomService.getRoomDetail(roomId));
    }

}
