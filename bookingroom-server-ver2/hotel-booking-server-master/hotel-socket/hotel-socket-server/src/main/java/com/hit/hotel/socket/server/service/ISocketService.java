package com.hit.hotel.socket.server.service;

public interface ISocketService {

    <T> void sendToRoom(String room, String eventName, T data);

}
