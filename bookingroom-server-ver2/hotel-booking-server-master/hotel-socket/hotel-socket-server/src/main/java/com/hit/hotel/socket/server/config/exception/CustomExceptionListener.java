package com.hit.hotel.socket.server.config.exception;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ExceptionListenerAdapter;
import com.hit.common.config.locale.Translator;
import com.hit.common.core.domain.model.ResponseStatusCode;
import com.hit.common.core.exception.BaseResponseException;
import com.hit.common.core.exception.ResponseStatusCodeEnum;
import com.hit.hotel.socket.client.constant.SocketConstants;
import com.hit.hotel.socket.server.message.MessageType;
import com.hit.hotel.socket.server.message.SocketMessage;
import com.hit.hotel.socket.server.util.SocketIOUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class CustomExceptionListener extends ExceptionListenerAdapter {

    @Override
    public void onEventException(Exception e, List<Object> messages, SocketIOClient client) {
        this.handleException(e, messages, client);
    }

    @Override
    public void onAuthException(Throwable throwable, SocketIOClient socketIOClient) {
        log.error("onAuth exception: {}", throwable.getMessage(), throwable);
    }

    private void handleException(Exception e, List<Object> messages, SocketIOClient client) {
        log.error("onEvent exception: {}", e.getMessage(), e);
        if (e.getCause() instanceof BaseResponseException exception) {
            this.sendErrorMessageToClient(messages, client, exception.getResponseStatusCode(), exception.getParams());
        } else {
            this.sendErrorMessageToClient(messages, client, ResponseStatusCodeEnum.INTERNAL_GENERAL_SERVER_ERROR, null);
        }
    }

    private void sendErrorMessageToClient(List<Object> messages, SocketIOClient client, ResponseStatusCode responseStatusCode, String[] params) {
        SocketIOUtils.setLocaleContext(client.getHandshakeData());
        client.sendEvent(SocketConstants.Event.ERROR, new SocketMessage()
                .setType(MessageType.CLIENT)
                .setCode(responseStatusCode.code())
                .setMessage(Translator.toLocale(responseStatusCode.code(), params))
                .setData(messages.isEmpty() ? null : messages.getFirst()));
    }
}