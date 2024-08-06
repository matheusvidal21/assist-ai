package com.ai.assist.service.impl;

import com.ai.assist.dto.LoginDto;
import com.ai.assist.dto.UpdateUserDto;
import com.ai.assist.dto.UserDto;
import com.ai.assist.dto.response.UserResponse;
import com.ai.assist.exception.BadRequestException;
import com.ai.assist.exception.NotFoundException;
import com.ai.assist.mapper.UserMapper;
import com.ai.assist.model.Role;
import com.ai.assist.model.User;
import com.ai.assist.repository.UserRepository;
import com.ai.assist.service.EmailService;
import com.ai.assist.service.RoleService;
import com.ai.assist.service.UserService;
import com.ai.assist.utils.EmailUtil;
import com.ai.assist.utils.PasswordUtils;
import com.ai.assist.utils.TemplatesHtml;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private EmailService emailService;

    @Override
    public List<UserResponse> findAll() {
        return this.userRepository.findAll().stream().map(UserMapper::fromEntityToResponse).toList();
    }

    @Override
    public UserResponse findById(Long id) {
        User entity = this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return UserMapper.fromEntityToResponse(entity);
    }

    @Override
    public UserResponse findByEmail(String email){
        User entity = this.userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User with email not found"));
        return UserMapper.fromEntityToResponse(entity);
    }

    @Override
    public Optional<User> findByUsername(String username){
        return this.userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserResponse create(UserDto user) {
        validate(user);
        Set<Role> roles = new HashSet<>(this.roleService.findAllByIds(user.getRolesIds().stream().toList()));
        User entity = UserMapper.fromDtoToEntity(user, roles);
        sendMessage(entity, roles, TemplatesHtml.CREATE_USER);
        entity.setPassword(PasswordUtils.encode(user.getPassword()));
        return UserMapper.fromEntityToResponse(this.userRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        var user = this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        sendMessage(user, null, TemplatesHtml.DELETE_USER);
        this.userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UserResponse update(Long id, UpdateUserDto user){
        User entity = this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));

        if (!entity.getEmail().equals(user.getEmail()) && this.userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new BadRequestException("Email already in use");
        }

        if (!entity.getUsername().equals(user.getUsername()) && this.userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new BadRequestException("Username already in use");
        }

        List<Role> roles = this.roleService.findAllByIds(user.getRolesIds().stream().toList());

        entity.setName(user.getName());
        entity.setUsername(user.getUsername());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setRoles(new HashSet<>(roles));
        entity.setEmail(user.getEmail());

        sendMessage(entity, entity.getRoles(), TemplatesHtml.UPDATE_USER);
        return UserMapper.fromEntityToResponse(this.userRepository.save(entity));
    }

    @Override
    public boolean isLoginValid(LoginDto loginDto, User user){
        return PasswordUtils.matches(loginDto.getPassword(), user.getPassword());
    }

    private void sendMessage(User user, Set<Role> roles, List<String> template) {
        Map<String, Object> variables = new HashMap<>();
        if (template.get(0).equals(TemplatesHtml.CREATE_USER.get(0))){
            variables = EmailUtil.buildEmailVariables(
                    "name", user.getName(),
                    "username", user.getUsername(),
                    "password", user.getPassword(),
                    "roles", roles.stream().map(Role::getName).toList()
            );
        } else if (template.get(0).equals(TemplatesHtml.UPDATE_USER.get(0))){
            variables = EmailUtil.buildEmailVariables(
                    "name", user.getName(),
                    "username", user.getUsername(),
                    "updatedAt", user.getUpdatedAt().toString(),
                    "roles", roles.stream().map(Role::getName).toList()
            );
        } else if (template.get(0).equals(TemplatesHtml.DELETE_USER.get(0))){
            variables = EmailUtil.buildEmailVariables(
                    "name", user.getName()
            );
        }

        try {
            emailService.sendHtmlEmail(user.getEmail(), template.get(0), template.get(1), variables);
        } catch (MessagingException e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
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
