package com.hit.hotel.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hit")
public class HotelAuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelAuthenticationApplication.class, args);
    }

}
