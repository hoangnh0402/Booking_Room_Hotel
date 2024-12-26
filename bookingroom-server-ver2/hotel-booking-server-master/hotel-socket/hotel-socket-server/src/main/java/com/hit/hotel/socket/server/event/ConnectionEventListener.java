package com.hit.hotel.socket.server.event;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.hit.common.core.jwt.JwtProvider;
import com.hit.hotel.socket.client.constant.SocketConstants;
import com.hit.hotel.socket.server.config.authentication.Authentication;
import com.hit.hotel.socket.server.store.SocketIOStore;
import com.hit.hotel.socket.server.util.SocketIOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class ConnectionEventListener {

    private final SocketIOStore socketIOStore;

    private final JwtProvider jwtProvider;

    @Value("${app.security.server-key}")
    private String serverKey;

    @OnConnect
    private void onConnect(SocketIOClient client) {
        log.debug("ClientSessionId:[{}] Connected to socket", client.getSessionId());
        Authentication authentication = SocketIOUtils.getAuthentication(client.getHandshakeData(), jwtProvider, serverKey);
        socketIOStore.putAuthentication(client, authentication);
        if (!Boolean.TRUE.equals(authentication.getIsSystem())) {
            for (String authority : authentication.getUser().getAuthorities()) {
                if ("ADMIN".equals(authority)) {
                    log.debug("ClientSessionId:[{}] join room [{}]", client.getSessionId(), SocketConstants.Room.ADMIN);
                    client.joinRoom(SocketConstants.Room.ADMIN);
                } else if ("USER".equals(authority)) {
                    log.debug("ClientSessionId:[{}] join room [{}]", client.getSessionId(), SocketConstants.Room.CLIENT);
                    client.joinRoom(SocketConstants.Room.CLIENT);
                }
            }
        }
    }

    @OnDisconnect
    private void onDisconnect(SocketIOClient client) {
        log.debug("ClientSessionId:[{}] - Disconnected from socket", client.getSessionId());
        socketIOStore.clearStore(client);
    }

}