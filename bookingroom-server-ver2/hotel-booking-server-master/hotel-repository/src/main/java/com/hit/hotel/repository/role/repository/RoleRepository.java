package com.hit.hotel.repository.role.repository;

import com.hit.hotel.repository.role.entity.Role;
import com.hit.jpa.BaseJPARepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BaseJPARepository<Role, Integer> {

    Role getRoleByName(String name);

}
