package com.ai.assist.controller;

import com.ai.assist.controller.routes.Routes;
import com.ai.assist.dto.UpdateUserDto;
import com.ai.assist.dto.UserDto;
import com.ai.assist.dto.response.UserResponse;
import com.ai.assist.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Routes.USERS)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<UserResponse>> getAll(){
        return ResponseEntity.ok(this.userService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<UserResponse> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponse> save(@RequestBody @Valid UserDto user){
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(this.userService.create(user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_USER', 'SCOPE_SUPPORT')")
    public ResponseEntity<UserResponse> update(@PathVariable("id") Long id, @RequestBody @Valid UpdateUserDto user){
        return ResponseEntity.ok(this.userService.update(id, user));
    }

}
