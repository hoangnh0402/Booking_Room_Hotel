package com.hit.hotel.socket.client.config;

import com.hit.common.core.constants.HeaderConstant;
import com.hit.common.core.json.JsonMapper;
import com.hit.hotel.socket.client.annotation.EnableSocketClient;
import com.hit.hotel.socket.client.constant.SocketConstants;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.engineio.client.transports.Polling;
import io.socket.engineio.client.transports.WebSocket;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

@Log4j2
@Configuration
@ConditionalOnBean(annotation = EnableSocketClient.class)
public class SocketClientConfig {

    @Value("${app.socket-server-endpoint}")
    private String socketServerEndpoint;

    @Value("${app.security.server-key}")
    private String serverKey;

    @Bean(destroyMethod = "disconnect")
    public Socket socketClient() {
        IO.Options options = IO.Options.builder()
                .setTransports(new String[] { Polling.NAME, WebSocket.NAME })
                .setTimeout(20000L)
                .setReconnection(true)
                .setExtraHeaders(new HashMap<>() {{
                    put(HeaderConstant.SERVER_TOKEN, List.of(serverKey));
                    put(HeaderConstant.USER_ID, List.of("0"));
                }})
                .build();
        Socket socket = IO.socket(URI.create(socketServerEndpoint), options);
        log.info("===> connect socket");
        socket.connect();
        socket.on(SocketConstants.Event.ERROR, objects -> {
            log.error("xxxxxx> socket receive event error: {}", JsonMapper.encode(objects));
        });
        return socket;
    }


}
