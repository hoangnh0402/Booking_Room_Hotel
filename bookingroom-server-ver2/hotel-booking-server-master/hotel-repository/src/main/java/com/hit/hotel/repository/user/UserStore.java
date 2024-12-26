package com.hit.hotel.repository.user;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.hotel.repository.user.entity.User;

public interface UserStore {

    User get(String userId);

    User save(User user);

    User update(User user);

    void delete(String userId);

    boolean exists(String userId);

    User getUserByEmailOrPhone(String email, String phone);

    User getUserByEmail(String email);

    Integer enableUserByEmail(String email);

    void updatePasswordByEmail(String email, String newPassword);

    void lockAndUnlockUserById(String userId, Boolean isLocked);

    boolean existsUserLocked(String userId);

    PaginationResponse<User> getCustomers(PaginationRequest paginationRequest, Boolean isEnable, Boolean isLocked);

}
