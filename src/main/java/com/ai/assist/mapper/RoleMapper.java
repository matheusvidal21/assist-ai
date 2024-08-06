package com.ai.assist.mapper;

import com.ai.assist.dto.RoleDto;
import com.ai.assist.model.Role;

public class RoleMapper {

    private RoleMapper(){}

    public static Role fromDtoToEntity(RoleDto dto){
        return Role.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

    public static RoleDto fromEntityToDto(Role role){
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}
