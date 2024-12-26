package com.hit.hotel.repository.role.adapter;

import com.hit.hotel.repository.role.RoleStore;
import com.hit.hotel.repository.role.entity.Role;
import com.hit.hotel.repository.role.repository.RoleRepository;
import com.hit.jpa.BaseJPAAdapter;
import org.springframework.stereotype.Component;

@Component
public class RoleAdapter extends BaseJPAAdapter<Role, Integer, RoleRepository> implements RoleStore {

    @Override
    protected Class<Role> getEntityClass() {
        return Role.class;
    }

    @Override
    public Role getRoleByName(String name) {
        return this.repository.getRoleByName(name);
    }

}

