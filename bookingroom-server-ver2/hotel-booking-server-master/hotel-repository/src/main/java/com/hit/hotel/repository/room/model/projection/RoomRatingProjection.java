package com.hit.hotel.repository.room.model.projection;

import com.hit.common.core.domain.dto.CreatorDTO;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface RoomRatingProjection {

    Integer getId();

    Integer getStar();

    String getComment();

    String getRoomId();

    LocalDateTime getCreatedDate();

    LocalDateTime getLastModifiedDate();

    @Value("#{new com.hit.common.core.domain.dto.CreatorDTO(target.creatorId, target.creatorFirstName, target.creatorLastName, target.creatorAvatar)}")
    CreatorDTO getCreator();

}
