package com.hit.hotel.admin.core.service.impl;

import com.hit.common.core.constants.CommonConstants;
import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.common.core.exception.BaseResponseException;
import com.hit.common.core.exception.ResponseStatusCodeEnum;
import com.hit.hotel.admin.core.data.mapper.RoomMapper;
import com.hit.hotel.admin.core.data.request.room.AddSaleRequest;
import com.hit.hotel.admin.core.data.request.room.CreateRoomRequest;
import com.hit.hotel.admin.core.data.request.room.RoomFilterRequest;
import com.hit.hotel.admin.core.data.request.room.UpdateRoomRequest;
import com.hit.hotel.admin.core.data.response.RoomAvailableResponse;
import com.hit.hotel.admin.core.data.response.RoomDetailResponse;
import com.hit.hotel.admin.core.data.response.RoomResponse;
import com.hit.hotel.admin.core.service.RoomService;
import com.hit.hotel.repository.media.MediaStore;
import com.hit.hotel.repository.media.entity.Media;
import com.hit.hotel.repository.room.RoomStore;
import com.hit.hotel.repository.room.entity.Room;
import com.hit.hotel.repository.room.model.condition.RoomFilterCondition;
import com.hit.hotel.repository.room.model.projection.RoomDetailPjt;
import com.hit.hotel.repository.sale.SaleStore;
import com.hit.hotel.repository.sale.entity.Sale;
import com.hit.hotel.repository.user.UserStore;
import com.hit.hotel.repository.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service("roomServiceAdmin")
public class RoomServiceImpl implements RoomService {

    private final RoomStore roomStore;

    private final SaleStore saleStore;

    private final MediaStore mediaStore;

    private final UserStore userStore;

    private final RoomMapper roomMapper;

    @Override
    public PaginationResponse<RoomResponse> getRooms(PaginationRequest paginationRequest) {
        return roomStore.getRoomDetailPjts(paginationRequest).map(roomMapper::toRoomResponse);
    }

    @Override
    public PaginationResponse<RoomAvailableResponse> getRoomsAvailable(PaginationRequest paginationRequest,
                                                                       RoomFilterRequest filterRequest) {
        RoomFilterCondition condition = roomMapper.toRoomFilterCondition(filterRequest);
        return roomStore.getRoomDetailAvailablePjts(paginationRequest, condition).map(roomMapper::toRoomAvailableResponse);
    }

    @Override
    public RoomDetailResponse getRoomDetail(Integer roomId) {
        RoomDetailPjt roomDetailPjt = roomStore.getRoomDetailPjt(roomId);
        List<Media> mediaActiveByRoomId = mediaStore.getMediaActiveByRoomId(roomDetailPjt.getId());
        return roomMapper.toRoomDetailResponse(roomDetailPjt, mediaActiveByRoomId);
    }

    @Override
    @Transactional
    public RoomResponse createRoom(UserPrincipal userPrincipal, CreateRoomRequest request) {
        Room room = roomMapper.toRoom(request);
        roomStore.save(room);
        Set<Media> medias = mediaStore.createMediaByRoom(room, request.getFiles());
        room.setMedias(medias);
        return roomMapper.toRoomResponse(room, userPrincipal);
    }

    @Override
    @Transactional
    public RoomResponse updateRoom(UserPrincipal userPrincipal, UpdateRoomRequest request) {
        Room room = roomStore.get(request.getRoomId());
        if (ObjectUtils.isEmpty(room)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.ROOM_NOT_FOUND);
        }
        roomMapper.updateRoom(request, room);
        roomStore.update(room);

        if (CollectionUtils.isNotEmpty(request.getDeleteMediaIds())) {
            mediaStore.deleteMediaByRoomIdAndMediaIdIn(request.getRoomId(), request.getDeleteMediaIds());
        }
        if (CollectionUtils.isNotEmpty(request.getNewMediaFiles())) {
            mediaStore.createMediaByRoom(room, request.getNewMediaFiles());
        }

        User creator = userStore.get(room.getCreatedBy());
        return roomMapper.toRoomResponse(room, creator, userPrincipal);
    }

    @Override
    public CommonResponse deleteRoom(Integer roomId) {
        if (Boolean.FALSE.equals(roomStore.exists(roomId))) {
            throw new BaseResponseException(ResponseStatusCodeEnum.ROOM_NOT_FOUND);
        }
        roomStore.deleteToTrashById(roomId);
        return new CommonResponse(Boolean.TRUE, CommonConstants.DELETE_SUCCESS);
    }

    @Override
    public CommonResponse deleteTrashRoom(Integer roomId) {
        if (Boolean.FALSE.equals(roomStore.existsTrash(roomId))) {
            throw new BaseResponseException(ResponseStatusCodeEnum.ROOM_NOT_FOUND);
        }
        roomStore.deleteTrashById(roomId);
        return new CommonResponse(Boolean.TRUE, CommonConstants.DELETE_SUCCESS);
    }

    @Override
    public CommonResponse restoreTrashRoom(Integer roomId) {
        if (Boolean.FALSE.equals(roomStore.existsTrash(roomId))) {
            throw new BaseResponseException(ResponseStatusCodeEnum.ROOM_NOT_FOUND);
        }
        roomStore.restoreTrashById(roomId);
        return new CommonResponse(Boolean.TRUE, CommonConstants.RESTORE_SUCCESS);
    }

    @Override
    public CommonResponse addSaleToRooms(AddSaleRequest request) {
        List<Room> allRoomInIds = roomStore.getAllInIds(request.getRoomIds());
        if (CollectionUtils.isEmpty(allRoomInIds) || allRoomInIds.size() != request.getRoomIds().size()) {
            throw new BaseResponseException(ResponseStatusCodeEnum.ROOM_NOT_FOUND);
        }

        String roomsOnSale = allRoomInIds.stream()
                .filter(room -> ObjectUtils.isNotEmpty(room.getSaleId()))
                .map(Room::getName)
                .collect(Collectors.joining(","));
        if (StringUtils.isNotEmpty(roomsOnSale)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.ROOM_ON_SALE, new String[]{roomsOnSale});
        }

        Sale sale = saleStore.get(request.getSaleId());
        if (ObjectUtils.isEmpty(sale)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.SALE_NOT_FOUND);
        }
        allRoomInIds.forEach(room -> room.setSale(sale));
        roomStore.saveAll(allRoomInIds);
        return new CommonResponse(Boolean.TRUE, CommonConstants.CREATE_SUCCESS);
    }

    @Override
    public CommonResponse deleteSaleFromRooms(List<Integer> roomIds) {
        List<Room> allRoomInIds = roomStore.getAllInIds(roomIds);
        if (CollectionUtils.isEmpty(allRoomInIds) || allRoomInIds.size() != roomIds.size()) {
            throw new BaseResponseException(ResponseStatusCodeEnum.ROOM_NOT_FOUND);
        }
        allRoomInIds.forEach(room -> room.setSale(null));
        roomStore.saveAll(allRoomInIds);
        return new CommonResponse(Boolean.TRUE, CommonConstants.DELETE_SUCCESS);
    }

}
