package com.hit.hotel.core.core.data.mapper;

import com.hit.hotel.core.core.data.request.RoomRatingCreateRequest;
import com.hit.hotel.core.core.data.request.RoomRatingUpdateRequest;
import com.hit.hotel.core.core.data.response.RoomRatingResponse;
import com.hit.hotel.repository.room.entity.RoomRating;
import com.hit.hotel.repository.room.model.projection.RoomRatingProjection;
import com.hit.hotel.repository.user.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RoomRatingMapper extends AuditingMapper {

    RoomRatingResponse toRoomRatingResponse(RoomRatingProjection projection);

    RoomRatingResponse toRoomRatingResponse(RoomRating roomRating, @Context User creator);

    RoomRating toRoomRating(RoomRatingCreateRequest request);

    void updateRoomRating(@MappingTarget RoomRating roomRating, RoomRatingUpdateRequest request);

    @AfterMapping
    default void afterToRoomRatingResponse(@MappingTarget RoomRatingResponse response, @Context User creator) {
        response.setCreator(this.toCreator(creator));
    }

}
