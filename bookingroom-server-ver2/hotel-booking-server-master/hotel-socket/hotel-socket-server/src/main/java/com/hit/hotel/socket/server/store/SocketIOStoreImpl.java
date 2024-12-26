package com.hit.hotel.socket.server.store;

import com.corundumstudio.socketio.SocketIOClient;
import com.hit.cache.store.external.ExternalCacheStore;
import com.hit.hotel.socket.server.config.authentication.Authentication;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocketIOStoreImpl implements SocketIOStore {

    private final ExternalCacheStore externalCacheStore;

    @Override
    public void putAuthentication(SocketIOClient client, Authentication value) {
        String sessionClientInfoKey = this.getSessionClientInfoKey(client.getSessionId().toString());
        externalCacheStore.putObjectToHash(sessionClientInfoKey, AUTHENTICATION, value);
    }

    @Override
    public Authentication getAuthentication(SocketIOClient client) {
        String sessionClientInfoKey = this.getSessionClientInfoKey(client.getSessionId().toString());
        return externalCacheStore.getObjectFromHash(sessionClientInfoKey, AUTHENTICATION, Authentication.class);
    }

    @Override
    public void clearStore(SocketIOClient client) {
        externalCacheStore.delete(this.getSessionClientInfoKey(client.getSessionId().toString()));
    }

    private String getSessionClientInfoKey(String sessionId) {
        return String.format(SESSION_CLIENT_INFO, sessionId);
    }

}
