package com.ai.assist.mapper;

import com.ai.assist.dto.UserDto;
import com.ai.assist.dto.response.UserResponse;
import com.ai.assist.model.Role;
import com.ai.assist.model.User;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    private UserMapper(){}

    public static User fromDtoToEntity(UserDto dto, Set<Role> roles){
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .roles(roles)
                .build();
    }

    public static UserResponse fromEntityToResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(RoleMapper::fromEntityToDto).collect(Collectors.toList()))
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

}
