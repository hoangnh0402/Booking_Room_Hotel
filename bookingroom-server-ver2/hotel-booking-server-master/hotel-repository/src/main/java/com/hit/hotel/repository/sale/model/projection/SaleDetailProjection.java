package com.hit.hotel.repository.sale.model.projection;

import com.hit.common.core.domain.dto.CreatorDTO;
import com.hit.common.core.domain.dto.ModifierDTO;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface SaleDetailProjection {

    Integer getId();

    LocalDateTime getDayStart();

    LocalDateTime getDayEnd();

    Integer getSalePercent();

    LocalDateTime getCreatedDate();

    LocalDateTime getLastModifiedDate();

    @Value("#{new com.hit.common.core.domain.dto.CreatorDTO(target.creatorId, target.creatorFirstName, target.creatorLastName, target.creatorAvatar)}")
    CreatorDTO getCreator();

    @Value("#{new com.hit.common.core.domain.dto.ModifierDTO(target.modifierId, target.modifierFirstName, target.modifierLastName, target.modifierAvatar)}")
    ModifierDTO getModifier();

}
