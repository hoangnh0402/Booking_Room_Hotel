package com.hit.hotel.repository.user.adapter;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.pagination.PaginationResponse;
import com.hit.common.utils.PaginationUtils;
import com.hit.hotel.repository.role.constants.RoleConstants;
import com.hit.hotel.repository.user.UserStore;
import com.hit.hotel.repository.user.entity.User;
import com.hit.hotel.repository.user.repository.UserRepository;
import com.hit.jpa.BaseJPAAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class UserAdapter extends BaseJPAAdapter<User, String, UserRepository> implements UserStore {

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public User getUserByEmailOrPhone(String email, String phone) {
        return this.repository.getUserByEmailOrPhoneNumber(email, phone).orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        return this.repository.getUserByEmail(email).orElse(null);
    }

    @Override
    public Integer enableUserByEmail(String email) {
        return this.repository.enableUserByEmail(email);
    }

    @Override
    public void updatePasswordByEmail(String email, String newPassword) {
        this.repository.updatePasswordByEmail(email, newPassword);
    }

    @Override
    public void lockAndUnlockUserById(String userId, Boolean isLocked) {
        this.repository.updateIsLockedById(userId, isLocked);
    }

    @Override
    public boolean existsUserLocked(String userId) {
        return this.repository.existsUserLockedById(userId);
    }

    @Override
    public PaginationResponse<User> getCustomers(PaginationRequest paginationRequest, Boolean isEnable, Boolean isLocked) {
        Pageable pageable = PaginationUtils.buildPageableNative(paginationRequest);
        Page<User> users = this.repository.getUsers(paginationRequest, isEnable, isLocked, RoleConstants.USER, pageable);
        return new PaginationResponse<>(paginationRequest, users);
    }

}

