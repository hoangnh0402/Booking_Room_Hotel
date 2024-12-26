package com.hit.hotel.socket.server.event;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.hit.common.core.exception.BaseResponseException;
import com.hit.common.core.exception.ResponseStatusCodeEnum;
import com.hit.common.core.json.JsonMapper;
import com.hit.hotel.socket.client.constant.SocketConstants;
import com.hit.hotel.socket.client.message.EventCommandMessage;
import com.hit.hotel.socket.server.config.authentication.Authentication;
import com.hit.hotel.socket.server.service.ISocketService;
import com.hit.hotel.socket.server.store.SocketIOStore;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class EventServerListener {

    private final ISocketService socketService;

    private final SocketIOStore socketIOStore;

    @SneakyThrows
    @OnEvent(SocketConstants.Event.EVENT_SERVER_COMMAND)
    public void eventServerCommand(SocketIOClient client, String commandMsg, AckRequest request) {
        EventCommandMessage command = JsonMapper.decodeValue(commandMsg, EventCommandMessage.class);
        log.debug("Socket eventServerCommand {}", JsonMapper.encode(command));
        Authentication authentication = socketIOStore.getAuthentication(client);
        if (!Boolean.TRUE.equals(authentication.getIsSystem())) {
            throw new BaseResponseException(ResponseStatusCodeEnum.FORBIDDEN_ERROR);
        } else if (SocketConstants.ROOMS.get(command.getRoom()) == null) {
            throw new BaseResponseException(ResponseStatusCodeEnum.RESOURCE_NOT_FOUND);
        } else if (SocketConstants.EVENTS.get(command.getEvent()) == null) {
            throw new BaseResponseException(ResponseStatusCodeEnum.RESOURCE_NOT_FOUND);
        }
        socketService.sendToRoom(command.getRoom(), command.getEvent(), command.getData());
    }

}