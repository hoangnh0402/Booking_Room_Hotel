package com.hit.hotel.socket.server;

import com.corundumstudio.socketio.SocketIOServer;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class SocketIOStarter {

    private final SocketIOServer server;

    @PostConstruct
    public void startSocketIOServer() {
        log.info("starting socket.io server");
        server.start();
    }

    @PreDestroy
    public void stopSocketIOServer() {
        log.info("stop socket.io server");
        server.stop();
    }
}