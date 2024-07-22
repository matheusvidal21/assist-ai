package com.ai.assist.mapper;

import com.ai.assist.dto.UserDto;
import com.ai.assist.dto.response.UserResponse;
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

    public static UserResponse fromEntityToResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().getValue())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

}
