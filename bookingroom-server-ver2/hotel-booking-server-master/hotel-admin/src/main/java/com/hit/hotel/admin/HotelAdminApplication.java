package com.hit.hotel.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hit.hotel")
public class HotelAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelAdminApplication.class, args);
    }

}
