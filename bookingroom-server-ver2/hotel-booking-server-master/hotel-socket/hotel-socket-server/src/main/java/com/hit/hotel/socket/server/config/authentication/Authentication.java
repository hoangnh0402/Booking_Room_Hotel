package com.hit.hotel.socket.server.config.authentication;

import com.hit.common.core.domain.model.SimpleSecurityUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Authentication {

    private SimpleSecurityUser user;

    private Boolean isSystem;

}
