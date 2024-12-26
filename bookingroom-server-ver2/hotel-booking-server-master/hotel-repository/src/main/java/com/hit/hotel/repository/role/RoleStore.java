package com.hit.hotel.repository.role;

import com.hit.hotel.repository.role.entity.Role;

public interface RoleStore {

    Role getRoleByName(String name);

}
