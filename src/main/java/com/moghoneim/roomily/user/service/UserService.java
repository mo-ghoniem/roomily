package com.moghoneim.roomily.user.service;

import com.moghoneim.roomily.user.dto.UpdateUserRequest;
import com.moghoneim.roomily.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();

    void delete(Long id);

    void updateUser(Long id, UpdateUserRequest request);

    void assignHostRole(Long id);

    UserDto getUserById(Long id);

    UserDto getCurrentUser();
}
