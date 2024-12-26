package com.hit.hotel.core.core.data.mapper;

import com.hit.common.core.domain.dto.CreatorDTO;
import com.hit.common.core.domain.dto.ModifierDTO;
import com.hit.hotel.repository.user.entity.User;

public interface AuditingMapper {

    CreatorDTO toCreator(User user);

    CreatorDTO toCreator(CreatorDTO user);

    ModifierDTO toModifier(User user);

    ModifierDTO toModifier(ModifierDTO user);

}
