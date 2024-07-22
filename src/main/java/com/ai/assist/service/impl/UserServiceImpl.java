package com.ai.assist.service.impl;

import com.ai.assist.dto.UserDto;
import com.ai.assist.exception.BadRequestException;
import com.ai.assist.exception.NotFoundException;
import com.ai.assist.mapper.UserMapper;
import com.ai.assist.model.User;
import com.ai.assist.model.enums.Role;
import com.ai.assist.repository.UserRepository;
import com.ai.assist.service.UserService;
import com.ai.assist.utils.PasswordUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.xml.validation.Validator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public User findByEmail(String email){
        return this.userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User with email not found"));
    }

    @Override
    public User create(UserDto user) {
        validate(user);
        User entity = UserMapper.fromDtoToEntity(user);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setPassword(PasswordUtils.encode(user.getPassword()));
        return this.userRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public User update(Long id, UserDto user){
        User entity = this.findById(id);
        entity.setName(user.getName());
        entity.setUsername(user.getUsername());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setRole(Role.fromValue(user.getRole()));

        if (!entity.getEmail().equals(user.getEmail()) && this.userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new BadRequestException("Email already in use");
        }

        if (!entity.getUsername().equals(user.getUsername()) && this.userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new BadRequestException("Username already in use");
        }

        return this.userRepository.save(entity);
    }

    private void validate(UserDto user){
        if (user.getId() != null){
            throw new BadRequestException("User id must be null");
        }

        if (this.userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new BadRequestException("Email already in use");
        }

        if (this.userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new BadRequestException("Username already in use");
        }

    }

}
