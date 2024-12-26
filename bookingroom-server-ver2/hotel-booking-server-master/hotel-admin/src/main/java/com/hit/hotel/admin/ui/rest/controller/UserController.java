package com.hit.hotel.admin.ui.rest.controller;

import com.hit.api.factory.GeneralResponse;
import com.hit.api.factory.ResponseFactory;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.admin.core.data.response.UserDetailResponse;
import com.hit.hotel.admin.core.data.response.UserResponse;
import com.hit.hotel.admin.core.service.UserService;
import com.hit.hotel.admin.ui.rest.UserOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("userControllerAdmin")
public class UserController implements UserOperations {

    private final UserService userService;

    @Override
    public ResponseEntity<GeneralResponse<PaginationResponse<UserResponse>>> getUsers(PaginationRequest paginationRequest,
                                                                                      Boolean isEnable,
                                                                                      Boolean isLocked) {
        return ResponseFactory.success(userService.getUsers(paginationRequest, isEnable, isLocked));
    }

    @Override
    public ResponseEntity<GeneralResponse<UserDetailResponse>> getUserDetail(String userId) {
        return ResponseFactory.success(userService.getUserDetail(userId));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> lockAndUnlockUser(String userId, Boolean isLocked) {
        if (Boolean.TRUE.equals(isLocked)) {
            return ResponseFactory.success(userService.lockUser(userId));
        } else {
            return ResponseFactory.success(userService.unlockUser(userId));
        }
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> deleteUserLocked(String userId) {
        return ResponseFactory.success(userService.deleteUserLocked(userId));
    }
}
