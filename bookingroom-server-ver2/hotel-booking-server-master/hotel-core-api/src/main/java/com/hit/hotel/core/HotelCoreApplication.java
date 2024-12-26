package com.hit.hotel.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hit")
public class HotelCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelCoreApplication.class, args);
    }

}
