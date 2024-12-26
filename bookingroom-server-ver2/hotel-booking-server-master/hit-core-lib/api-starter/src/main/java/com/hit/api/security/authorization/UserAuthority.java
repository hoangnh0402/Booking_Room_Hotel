package com.hit.api.security.authorization;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.security.core.GrantedAuthority;

public record UserAuthority(String authority) implements GrantedAuthority {

    @JsonCreator
    public UserAuthority(String authority) {
        this.authority = "ROLE_" + authority.toUpperCase();
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof UserAuthority userAuthority) {
            return this.authority.equals(userAuthority.authority);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.authority.hashCode();
    }
}
