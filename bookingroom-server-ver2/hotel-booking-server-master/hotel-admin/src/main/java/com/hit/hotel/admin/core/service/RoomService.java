package com.hit.hotel.admin.core.service;

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

import java.util.List;

public interface RoomService {

    PaginationResponse<RoomResponse> getRooms(PaginationRequest paginationRequest);

    PaginationResponse<RoomAvailableResponse> getRoomsAvailable(PaginationRequest paginationRequest,
                                                                RoomFilterRequest filterRequest);

    RoomDetailResponse getRoomDetail(Integer roomId);

    RoomResponse createRoom(UserPrincipal userPrincipal, CreateRoomRequest request);

    RoomResponse updateRoom(UserPrincipal userPrincipal, UpdateRoomRequest request);

    CommonResponse deleteRoom(Integer roomId);

    CommonResponse deleteTrashRoom(Integer roomId);

    CommonResponse restoreTrashRoom(Integer roomId);

    CommonResponse addSaleToRooms(AddSaleRequest request);

    CommonResponse deleteSaleFromRooms(List<Integer> roomIds);

}
