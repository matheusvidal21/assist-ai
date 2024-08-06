package com.ai.assist.service;

import com.ai.assist.dto.LoginDto;
import com.ai.assist.dto.UpdateUserDto;
import com.ai.assist.dto.UserDto;
import com.ai.assist.dto.response.UserResponse;
import com.ai.assist.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserResponse> findAll();

    UserResponse findById(Long id);

    UserResponse findByEmail(String email);

    Optional<User> findByUsername(String username);

    UserResponse create(UserDto user);

    void delete(Long id);

    UserResponse update(Long id, UpdateUserDto user);

    boolean isLoginValid(LoginDto loginDto, User user);
}
