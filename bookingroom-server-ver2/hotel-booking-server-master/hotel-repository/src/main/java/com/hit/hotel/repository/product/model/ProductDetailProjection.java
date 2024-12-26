package com.hit.hotel.repository.product.model;

import com.hit.common.core.domain.dto.CreatorDTO;
import com.hit.common.core.domain.dto.ModifierDTO;
import com.hit.hotel.repository.service.model.ServiceDTO;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface ProductDetailProjection {

    Integer getId();

    String getName();

    String getThumbnail();

    String getDescription();

    LocalDateTime getCreatedDate();

    LocalDateTime getLastModifiedDate();

    @Value("#{new com.hit.hotel.repository.service.model.ServiceDTO(target.serviceId, target.serviceTitle, target.serviceThumbnail, target.servicePrice)}")
    ServiceDTO getService();

    @Value("#{new com.hit.common.core.domain.dto.CreatorDTO(target.creatorId, target.creatorFirstName, target.creatorLastName, target.creatorAvatar)}")
    CreatorDTO getCreator();

    @Value("#{new com.hit.common.core.domain.dto.ModifierDTO(target.modifierId, target.modifierFirstName, target.modifierLastName, target.modifierAvatar)}")
    ModifierDTO getModifier();

}
