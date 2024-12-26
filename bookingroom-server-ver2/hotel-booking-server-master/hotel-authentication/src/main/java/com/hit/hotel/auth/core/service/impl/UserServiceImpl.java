package com.hit.hotel.auth.core.service.impl;


import com.hit.common.utils.UploadFileUtils;
import com.hit.hotel.auth.core.domain.mapper.UserMapper;
import com.hit.hotel.auth.core.domain.request.UserCreateRequest;
import com.hit.hotel.auth.core.service.UserService;
import com.hit.hotel.repository.role.RoleStore;
import com.hit.hotel.repository.role.constants.RoleConstants;
import com.hit.hotel.repository.user.UserStore;
import com.hit.hotel.repository.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserStore userStore;

    private final RoleStore roleStore;

    private final UserMapper userMapper;

    private final UploadFileUtils uploadFileUtils;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserCreateRequest request) {
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(roleStore.getRoleByName(RoleConstants.USER));
        if (ObjectUtils.isNotEmpty(request.getAvatarFile())) {
            user.setAvatar(uploadFileUtils.uploadFile(request.getAvatarFile()));
        }
        userStore.save(user);
    }

}
