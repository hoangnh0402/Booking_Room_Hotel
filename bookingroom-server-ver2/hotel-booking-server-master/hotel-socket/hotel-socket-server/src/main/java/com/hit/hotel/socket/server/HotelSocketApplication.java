package com.hit.hotel.socket.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hit")
public class HotelSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelSocketApplication.class, args);
    }

}
