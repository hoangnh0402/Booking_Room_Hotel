package com.hit.hotel.common;

import com.hit.hotel.socket.client.annotation.EnableSocketClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSocketClient
@SpringBootApplication(scanBasePackages = "com.hit.hotel")
public class HotelCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelCommonApplication.class, args);
    }

}
