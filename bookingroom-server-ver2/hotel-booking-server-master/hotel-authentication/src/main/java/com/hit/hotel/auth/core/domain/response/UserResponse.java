package com.hit.hotel.auth.core.domain.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class UserResponse {

    private String id;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String gender;

    private LocalDate birthday;

    private String address;

    private String avatar;

}
