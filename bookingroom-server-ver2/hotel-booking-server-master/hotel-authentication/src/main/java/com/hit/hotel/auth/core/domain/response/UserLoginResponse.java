package com.hit.hotel.auth.core.domain.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class UserLoginResponse {

    private String tokenType = "Bearer";

    private String refreshToken;

    private String accessToken;

    private List<String> authorities; 

}
