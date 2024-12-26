package com.hit.hotel.admin.core.data.mapper;

import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.hotel.admin.core.data.request.room.CreateRoomRequest;
import com.hit.hotel.admin.core.data.request.room.RoomFilterRequest;
import com.hit.hotel.admin.core.data.request.room.UpdateRoomRequest;
import com.hit.hotel.admin.core.data.response.RoomAvailableResponse;
import com.hit.hotel.admin.core.data.response.RoomDetailResponse;
import com.hit.hotel.admin.core.data.response.RoomResponse;
import com.hit.hotel.admin.core.data.response.RoomSummaryResponse;
import com.hit.hotel.common.domain.response.MediaResponse;
import com.hit.hotel.repository.bookingdetail.model.StatisticRoomBookedProjection;
import com.hit.hotel.repository.media.entity.Media;
import com.hit.hotel.repository.room.entity.Room;
import com.hit.hotel.repository.room.model.condition.RoomFilterCondition;
import com.hit.hotel.repository.room.model.projection.RoomDetailAvailablePjt;
import com.hit.hotel.repository.room.model.projection.RoomDetailPjt;
import com.hit.hotel.repository.user.entity.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigInteger;
import java.util.List;

@Mapper(componentModel = "spring", implementationName = "<CLASS_NAME>AdminImpl")
public interface RoomMapper extends AuditingMapper {

    RoomResponse toRoomResponse(RoomDetailPjt projection);

    RoomAvailableResponse toRoomAvailableResponse(RoomDetailAvailablePjt projection);

    @Mapping(target = "creator", expression = "java(this.toCreator(userPrincipal))")
    @Mapping(target = "modifier", expression = "java(this.toModifier(userPrincipal))")
    RoomResponse toRoomResponse(Room room, @Context UserPrincipal userPrincipal);

    @Mapping(target = "creator", expression = "java(this.toCreator(creator))")
    @Mapping(target = "modifier", expression = "java(this.toModifier(modifier))")
    RoomResponse toRoomResponse(Room room, @Context User creator, @Context UserPrincipal modifier);

    @Mapping(target = "medias", expression = "java(this.toMediaResponses(medias))")
    RoomDetailResponse toRoomDetailResponse(RoomDetailPjt room, @Context List<Media> medias);

    RoomSummaryResponse toRoomSummaryResponse(StatisticRoomBookedProjection projection);

    Room toRoom(CreateRoomRequest request);

    @Mapping(target = "medias", ignore = true)
    void updateRoom(UpdateRoomRequest request, @MappingTarget Room room);

    List<MediaResponse> toMediaResponses(List<Media> medias);

    RoomFilterCondition toRoomFilterCondition(RoomFilterRequest filterRequest);

    default Boolean mapToBoolean(BigInteger bigInteger) {
        return !BigInteger.ZERO.equals(bigInteger);
    }

}
