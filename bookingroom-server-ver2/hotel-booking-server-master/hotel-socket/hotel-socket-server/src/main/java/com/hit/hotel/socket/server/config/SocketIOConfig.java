package com.hit.hotel.socket.server.config;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Transport;
import com.corundumstudio.socketio.protocol.JacksonJsonSupport;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hit.hotel.socket.server.config.authentication.CustomAuthorizationListener;
import com.hit.hotel.socket.server.config.exception.CustomExceptionListener;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
@org.springframework.context.annotation.Configuration
public class SocketIOConfig {

    @Value("${server.socket.host}")
    private String host;

    @Value("${server.socket.port}")
    private Integer port;

    private final CustomAuthorizationListener authorizationListener;

    private final CustomExceptionListener exceptionListener;

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setHostname(host);
        config.setPort(port);
        config.setTransports(Transport.WEBSOCKET, Transport.POLLING);
        config.setAuthorizationListener(authorizationListener);
        config.setExceptionListener(exceptionListener);
        config.setOrigin("*");
        config.setAllowHeaders("*");
        config.setJsonSupport(this.jacksonJsonSupport());
        return new SocketIOServer(config);
    }

    // For enable socket.io annotation ( @onConnect, @onEvent,...)
    @Bean
    public SocketIOAnnotationScanner socketIOAnnotationScanner(SocketIOServer socketServer) {
        return new SocketIOAnnotationScanner(socketServer);
    }

    public JacksonJsonSupport jacksonJsonSupport() {
        return new JacksonJsonSupport(new JavaTimeModule());
    }
}
