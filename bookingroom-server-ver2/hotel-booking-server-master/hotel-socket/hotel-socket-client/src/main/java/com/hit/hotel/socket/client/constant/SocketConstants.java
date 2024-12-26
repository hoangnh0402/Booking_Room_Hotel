package com.hit.hotel.socket.client.constant;

import com.hit.common.core.exception.BusinessException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SocketConstants {

    public static final Map<String, Boolean> ROOMS;
    public static final Map<String, Boolean> EVENTS;

    static {
        ROOMS = Collections.unmodifiableMap(
                Arrays.stream(Room.class.getDeclaredFields())
                        .filter(field -> field.getType().equals(String.class))
                        .collect(Collectors.toMap(field -> {
                            try {
                                return (String) field.get(null);
                            } catch (Exception e) {
                                throw new BusinessException(e);
                            }
                        }, field -> true))
        );
        EVENTS = Collections.unmodifiableMap(
                Arrays.stream(Event.class.getDeclaredFields())
                        .filter(field -> field.getType().equals(String.class))
                        .collect(Collectors.toMap(field -> {
                            try {
                                return (String) field.get(null);
                            } catch (Exception e) {
                                throw new BusinessException(e);
                            }
                        }, field -> true))
        );
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Room {
        public static final String ADMIN = "admin";
        public static final String CLIENT = "client";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Event {
        public static final String JOIN_ROOM = "join_room";
        public static final String LEAVE_ROOM = "leave_room";
        public static final String ERROR = "error";
        public static final String EVENT_SERVER_COMMAND = "event_server_command";
        public static final String NOTIFICATION = "notification";
        public static final String ROOMS_STATUS = "rooms_status";
    }

}
