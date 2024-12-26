package com.hit.hotel.core.core.data.mapper;

import com.hit.hotel.core.core.data.response.RoomDetailResponse;
import com.hit.hotel.core.core.data.response.RoomResponse;
import com.hit.hotel.repository.media.entity.Media;
import com.hit.hotel.repository.room.model.projection.RoomAvailablePjt;
import com.hit.hotel.repository.room.model.projection.RoomDetailPjt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigInteger;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper extends AuditingMapper {

    @Mapping(target = "medias", source = "medias")
    RoomResponse toRoomResponse(RoomAvailablePjt projection, List<Media> medias);

    @Mapping(target = "medias", source = "medias")
    RoomDetailResponse toRoomDetailResponse(RoomDetailPjt projection, List<Media> medias);

    default Boolean mapToBoolean(BigInteger bigInteger) {
        return !BigInteger.ZERO.equals(bigInteger);
    }

}
