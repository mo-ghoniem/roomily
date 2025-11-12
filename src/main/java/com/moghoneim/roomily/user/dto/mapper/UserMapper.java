package com.moghoneim.roomily.user.dto.mapper;

import com.moghoneim.roomily.role.dto.RoleDto;
import com.moghoneim.roomily.role.dto.mapper.RoleMapper;
import com.moghoneim.roomily.role.entity.Role;
import com.moghoneim.roomily.user.dto.UserDto;
import com.moghoneim.roomily.user.entity.User;
import org.springframework.stereotype.Component;


import java.util.List;



@Component
public class UserMapper {
    public UserDto toDto(User user) {
        List<RoleDto> roles = user.getRoles().stream().
                map(role -> new RoleMapper().toDto(role)).toList();
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roles(roles)
                .build();
    }

    public User toEntity(UserDto userDto) {
        List<Role> roles = userDto.getRoles().stream().
                map(role -> new RoleMapper().toEntity(role)).toList();
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .roles(roles)
                .build();
    }
}
