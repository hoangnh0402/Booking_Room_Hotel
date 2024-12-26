package com.hit.hotel.admin.core.data.mapper;

import com.hit.common.core.domain.dto.CreatorDTO;
import com.hit.common.core.domain.dto.ModifierDTO;
import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.hotel.repository.user.entity.User;
import org.mapstruct.Mapping;

public interface AuditingMapper {

    CreatorDTO toCreator(User user);

    CreatorDTO toCreator(CreatorDTO user);

    ModifierDTO toModifier(User user);

    ModifierDTO toModifier(ModifierDTO user);

    @Mapping(target = ".", source = "principal.user")
    CreatorDTO toCreator(UserPrincipal principal);

    @Mapping(target = ".", source = "principal.user")
    ModifierDTO toModifier(UserPrincipal principal);
}
