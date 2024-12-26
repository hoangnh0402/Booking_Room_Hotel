package com.hit.hotel.socket.client.service;

import com.hit.common.core.json.JsonMapper;
import com.hit.hotel.socket.client.annotation.EnableSocketClient;
import com.hit.hotel.socket.client.constant.SocketConstants;
import com.hit.hotel.socket.client.message.EventCommandMessage;
import io.socket.client.Socket;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
@ConditionalOnBean(annotation = EnableSocketClient.class)
public class SocketClientService {

    private final Socket socketClient;

    public void sendCommand(String room, String event, Object data) {
        EventCommandMessage command = new EventCommandMessage(room, event, data);
        String dataEncode = JsonMapper.encode(command);
        log.debug("[SocketClientService] sendCommand: {}", dataEncode);
        socketClient.emit(SocketConstants.Event.EVENT_SERVER_COMMAND, dataEncode);
    }

}
