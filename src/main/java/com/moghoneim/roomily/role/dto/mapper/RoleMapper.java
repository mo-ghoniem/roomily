package com.moghoneim.roomily.role.dto.mapper;

import com.moghoneim.roomily.role.dto.RoleDto;
import com.moghoneim.roomily.role.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public Role toEntity(RoleDto roleDto){
        return Role.builder()
                .id(roleDto.getId())
                .name(roleDto.getName())
                .build();
    }

    public RoleDto toDto(Role role){
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}
