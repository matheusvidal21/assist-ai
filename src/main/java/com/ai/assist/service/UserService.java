package com.ai.assist.service;

import com.ai.assist.dto.UserDto;
import com.ai.assist.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User findByEmail(String email);

    User create(UserDto user);

    void delete(Long id);

    User update(Long id, UserDto user);

}
