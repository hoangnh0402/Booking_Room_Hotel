package com.hit.hotel.auth.core.domain.mapper;

import com.hit.hotel.auth.core.domain.request.UserCreateRequest;
import com.hit.hotel.auth.core.domain.response.UserResponse;
import com.hit.hotel.repository.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {

    User toUser(UserCreateRequest request);

    UserResponse toUserResponse(User user);

}
