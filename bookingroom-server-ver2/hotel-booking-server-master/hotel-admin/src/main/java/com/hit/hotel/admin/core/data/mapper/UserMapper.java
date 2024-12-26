package com.hit.hotel.admin.core.data.mapper;

import com.hit.hotel.admin.core.data.response.UserResponse;
import com.hit.hotel.admin.core.data.response.UserDetailResponse;
import com.hit.hotel.repository.booking.model.projection.StatisticCustomerTopBookingProjection;
import com.hit.hotel.repository.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", implementationName = "<CLASS_NAME>AdminImpl")
public interface UserMapper extends AuditingMapper {

    UserResponse toUserResponse(User user);

    UserResponse toUserResponse(StatisticCustomerTopBookingProjection user);

    UserDetailResponse toUserDetailResponse(User user);

}
