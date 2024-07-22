package com.ai.assist.mapper;

import com.ai.assist.dto.UserDto;
import com.ai.assist.model.User;
import com.ai.assist.model.enums.Role;

public class UserMapper {

    private UserMapper(){}

    public static User fromDtoToEntity(UserDto dto){
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .role(Role.fromValue(dto.getRole()))
                .build();
    }

}
