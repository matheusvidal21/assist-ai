package com.ai.assist.service.impl;

import com.ai.assist.exception.NotFoundException;
import com.ai.assist.model.Role;
import com.ai.assist.repository.RoleRepository;
import com.ai.assist.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role not found"));
    }

    @Override
    public List<Role> findAllByIds(List<Long> ids) {
        List<Role> roles = roleRepository.findAllById(ids);

        if (roles.isEmpty()){
            throw new NotFoundException("Roles not found");
        }

        return roles;
    }
}
