package com.hit.hotel.repository.role.constants;

import com.hit.common.core.domain.model.UserPrincipal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleConstants {

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    public static boolean isAdmin(UserPrincipal userPrincipal) {
        return userPrincipal.getUser().getAuthorities().stream()
                .anyMatch(authority -> !RoleConstants.ADMIN.equals(authority));
    }

}
