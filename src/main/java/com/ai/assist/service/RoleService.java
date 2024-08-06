package com.ai.assist.service;

import com.ai.assist.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {

    List<Role> findAll();

    Role findById(Long id);

    List<Role> findAllByIds(List<Long> ids);

}
