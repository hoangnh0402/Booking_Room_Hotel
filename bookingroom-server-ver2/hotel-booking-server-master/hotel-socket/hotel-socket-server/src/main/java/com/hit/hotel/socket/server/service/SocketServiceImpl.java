package com.hit.hotel.socket.server.service;

import com.corundumstudio.socketio.SocketIOServer;
import com.hit.common.core.exception.ResponseStatusCodeEnum;
import com.hit.common.core.json.JsonMapper;
import com.hit.hotel.socket.server.message.MessageType;
import com.hit.hotel.socket.server.message.SocketMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class SocketServiceImpl implements ISocketService {

    private final SocketIOServer socketIOServer;

    @Override
    public <T> void sendToRoom(String room, String eventName, T data) {
        SocketMessage socketMessage = new SocketMessage()
                .setRoom(room)
                .setType(MessageType.SERVER)
                .setCode(ResponseStatusCodeEnum.SUCCESS.code())
                .setMessage(ResponseStatusCodeEnum.SUCCESS.code())
                .setData(data);
        log.info("Server send event to room:[{}], eventName: [{}], message: [{}]", room, eventName, JsonMapper.encode(socketMessage));
        socketIOServer.getRoomOperations(room).sendEvent(eventName, socketMessage);
    }

}
