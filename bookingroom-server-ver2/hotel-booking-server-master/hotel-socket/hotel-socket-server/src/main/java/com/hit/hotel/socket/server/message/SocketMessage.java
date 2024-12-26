package com.hit.hotel.socket.server.message;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SocketMessage {

    private String room;
    private MessageType type;
    private String code;
    private String message;
    private Object data;

}
