package com.hit.hotel.socket.server.store;

import com.corundumstudio.socketio.SocketIOClient;
import com.hit.hotel.socket.server.config.authentication.Authentication;

public interface SocketIOStore {

    String SESSION_CLIENT_INFO = "SESSION_CLIENT_INFO:%s";
    String AUTHENTICATION = "AUTHENTICATION";

    void putAuthentication(SocketIOClient client, Authentication value);

    Authentication getAuthentication(SocketIOClient client);

    void clearStore(SocketIOClient client);

}
