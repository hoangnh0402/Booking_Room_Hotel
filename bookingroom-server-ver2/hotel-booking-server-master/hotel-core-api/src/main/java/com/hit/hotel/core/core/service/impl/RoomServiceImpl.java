package com.hit.hotel.core.core.service.impl;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.exception.BaseResponseException;
import com.hit.common.core.exception.ResponseStatusCodeEnum;
import com.hit.common.core.json.JsonMapper;
import com.hit.hotel.common.domain.request.RoomFilterRequest;
import com.hit.hotel.core.core.data.mapper.RoomMapper;
import com.hit.hotel.core.core.data.response.RoomDetailResponse;
import com.hit.hotel.core.core.data.response.RoomResponse;
import com.hit.hotel.core.core.service.RoomService;
import com.hit.hotel.repository.media.MediaStore;
import com.hit.hotel.repository.media.entity.Media;
import com.hit.hotel.repository.room.RoomStore;
import com.hit.hotel.repository.room.model.condition.RoomFilterCondition;
import com.hit.hotel.repository.room.model.projection.RoomAvailablePjt;
import com.hit.hotel.repository.room.model.projection.RoomDetailPjt;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomStore roomStore;

    private final MediaStore mediaStore;

    private final RoomMapper roomMapper;

    @Override
    public PaginationResponse<RoomResponse> getRooms(PaginationRequest paginationRequest,
                                                     RoomFilterRequest filterRequest) {
        RoomFilterCondition condition = JsonMapper.getObjectMapper().convertValue(filterRequest, RoomFilterCondition.class);
        PaginationResponse<RoomAvailablePjt> roomPagination = roomStore.getRoomAvailablePjts(paginationRequest, condition);
        List<Integer> roomIds = roomPagination.getItems().stream().map(RoomAvailablePjt::getId).toList();
        Map<Integer, List<Media>> mediasGroupByRoomId = mediaStore.getMediaActiveInRoomIds(roomIds).stream()
                .collect(Collectors.groupingBy(Media::getRoomId));
        return roomPagination.map(item -> roomMapper.toRoomResponse(item, mediasGroupByRoomId.get(item.getId())));
    }

    @Override
    public RoomDetailResponse getRoomDetail(Integer roomId) {
        RoomDetailPjt roomDetail = roomStore.getRoomDetailPjt(roomId);
        if (ObjectUtils.isEmpty(roomDetail)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.ROOM_NOT_FOUND);
        }
        return roomMapper.toRoomDetailResponse(roomDetail, mediaStore.getMediaActiveByRoomId(roomId));
    }

}
