package com.hit.hotel.admin.core.service.impl;

import com.hit.common.core.constants.CommonConstants;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.common.core.exception.BaseResponseException;
import com.hit.common.core.exception.ResponseStatusCodeEnum;
import com.hit.hotel.admin.core.data.mapper.UserMapper;
import com.hit.hotel.admin.core.data.response.UserDetailResponse;
import com.hit.hotel.admin.core.data.response.UserResponse;
import com.hit.hotel.admin.core.service.UserService;
import com.hit.hotel.repository.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("userServiceAdmin")
public class UserServiceImpl implements UserService {

    private final UserStore userStore;

    private final UserMapper userMapper;

    @Override
    public PaginationResponse<UserResponse> getUsers(PaginationRequest paginationRequest,
                                                     Boolean isEnable, Boolean isLocked) {
        return userStore.getCustomers(paginationRequest, isEnable, isLocked).map(userMapper::toUserResponse);
    }

    @Override
    public UserDetailResponse getUserDetail(String id) {
        return userMapper.toUserDetailResponse(userStore.get(id));
    }

    @Override
    public CommonResponse lockUser(String userId) {
        if (!userStore.exists(userId)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.USER_NOT_FOUND);
        }
        userStore.lockAndUnlockUserById(userId, Boolean.TRUE);
        return new CommonResponse(Boolean.TRUE, CommonConstants.LOCK_SUCCESS);
    }

    @Override
    public CommonResponse unlockUser(String userId) {
        if (!userStore.exists(userId)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.USER_NOT_FOUND);
        }
        userStore.lockAndUnlockUserById(userId, Boolean.FALSE);
        return new CommonResponse(Boolean.TRUE, CommonConstants.UNLOCK_SUCCESS);
    }

    @Override
    public CommonResponse deleteUserLocked(String userId) {
        if (!userStore.existsUserLocked(userId)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.USER_UNLOCKED_NOT_FOUND);
        }
        userStore.delete(userId);
        return new CommonResponse(Boolean.TRUE, CommonConstants.DELETE_SUCCESS);
    }
}
