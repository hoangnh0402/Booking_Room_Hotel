package com.hit.hotel.admin.core.service;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.admin.core.data.response.UserDetailResponse;
import com.hit.hotel.admin.core.data.response.UserResponse;

public interface UserService {

    PaginationResponse<UserResponse> getUsers(PaginationRequest paginationRequest, Boolean isEnable, Boolean isLocked);

    UserDetailResponse getUserDetail(String id);

    CommonResponse lockUser(String userId);

    CommonResponse unlockUser(String userId);

    CommonResponse deleteUserLocked(String userId);

}
