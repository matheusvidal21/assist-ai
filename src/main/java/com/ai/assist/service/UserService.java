package com.ai.assist.service;

import com.ai.assist.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User save(User user);

    void delete(Long id);

}
