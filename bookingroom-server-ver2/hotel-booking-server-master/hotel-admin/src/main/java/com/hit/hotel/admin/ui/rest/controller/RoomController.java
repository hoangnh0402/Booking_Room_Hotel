package com.hit.hotel.admin.ui.rest.controller;

import com.hit.api.factory.GeneralResponse;
import com.hit.api.factory.ResponseFactory;
import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.admin.core.data.request.room.AddSaleRequest;
import com.hit.hotel.admin.core.data.request.room.CreateRoomRequest;
import com.hit.hotel.admin.core.data.request.room.RoomFilterRequest;
import com.hit.hotel.admin.core.data.request.room.UpdateRoomRequest;
import com.hit.hotel.admin.core.data.response.RoomAvailableResponse;
import com.hit.hotel.admin.core.data.response.RoomDetailResponse;
import com.hit.hotel.admin.core.data.response.RoomResponse;
import com.hit.hotel.admin.core.service.RoomService;
import com.hit.hotel.admin.ui.rest.RoomOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController("roomControllerAdmin")
public class RoomController implements RoomOperations {

    private final RoomService roomService;

    @Override
    public ResponseEntity<GeneralResponse<PaginationResponse<RoomResponse>>> getRooms(PaginationRequest paginationRequest) {
        return ResponseFactory.success(roomService.getRooms(paginationRequest));
    }

    @Override
    public ResponseEntity<GeneralResponse<PaginationResponse<RoomAvailableResponse>>> getRoomsAvailable(PaginationRequest paginationRequest, RoomFilterRequest filterRequest) {
        return ResponseFactory.success(roomService.getRoomsAvailable(paginationRequest, filterRequest));
    }

    @Override
    public ResponseEntity<GeneralResponse<RoomDetailResponse>> getRoomDetail(Integer roomId) {
        return ResponseFactory.success(roomService.getRoomDetail(roomId));
    }

    @Override
    public ResponseEntity<GeneralResponse<RoomResponse>> createRoom(UserPrincipal userPrincipal,
                                                                    CreateRoomRequest request) {
        return ResponseFactory.success(roomService.createRoom(userPrincipal, request));
    }

    @Override
    public ResponseEntity<GeneralResponse<RoomResponse>> updateRoom(UserPrincipal userPrincipal, Integer roomId,
                                                                    UpdateRoomRequest request) {
        request.setRoomId(roomId);
        return ResponseFactory.success(roomService.updateRoom(userPrincipal, request));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> deleteRoom(Integer roomId) {
        return ResponseFactory.success(roomService.deleteRoom(roomId));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> deleteTrashRoom(Integer roomId) {
        return ResponseFactory.success(roomService.deleteTrashRoom(roomId));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> restoreTrashRoom(Integer roomId) {
        return ResponseFactory.success(roomService.restoreTrashRoom(roomId));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> addSaleToRooms(AddSaleRequest request) {
        return ResponseFactory.success(roomService.addSaleToRooms(request));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> deleteSaleFromRooms(List<Integer> roomIds) {
        return ResponseFactory.success(roomService.deleteSaleFromRooms(roomIds));
    }
}
