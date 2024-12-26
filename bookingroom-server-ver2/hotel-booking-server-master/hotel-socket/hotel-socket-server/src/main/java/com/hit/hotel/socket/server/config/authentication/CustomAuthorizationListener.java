package com.hit.hotel.socket.server.config.authentication;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.AuthorizationResult;
import com.corundumstudio.socketio.HandshakeData;
import com.hit.common.core.jwt.JwtProvider;
import com.hit.hotel.socket.server.util.SocketIOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class CustomAuthorizationListener implements AuthorizationListener {

    private final JwtProvider jwtProvider;

    @Value("${app.security.server-key}")
    private String serverKey;

    @Override
    public AuthorizationResult getAuthorizationResult(HandshakeData handshakeData) {
        String accessControlRequestMethod = handshakeData.getHttpHeaders().get("access-control-request-method");
        if (StringUtils.isNotEmpty(accessControlRequestMethod)) {
            return AuthorizationResult.SUCCESSFUL_AUTHORIZATION;
        }

        log.debug("[Socket] getAuthorizationResult with client from {}", handshakeData.getLocal().getHostName());
        Authentication authentication = SocketIOUtils.getAuthentication(handshakeData, jwtProvider, serverKey);
        if (authentication.getUser() == null) {
            log.error("[Socket] Socket error FAILED_AUTHORIZATION");
            return AuthorizationResult.FAILED_AUTHORIZATION;
        }
        return AuthorizationResult.SUCCESSFUL_AUTHORIZATION;
    }

}
