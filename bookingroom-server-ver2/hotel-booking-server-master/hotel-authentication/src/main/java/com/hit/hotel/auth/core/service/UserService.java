package com.hit.hotel.auth.core.service;

import com.hit.hotel.auth.core.domain.request.UserCreateRequest;

public interface UserService {

    void createUser(UserCreateRequest request);

}
