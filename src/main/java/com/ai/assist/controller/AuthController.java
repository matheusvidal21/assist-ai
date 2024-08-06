package com.ai.assist.controller;

import com.ai.assist.controller.routes.Routes;
import com.ai.assist.dto.LoginDto;
import com.ai.assist.dto.response.LoginResponse;
import com.ai.assist.service.impl.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.AUTH)
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginDto loginDto){
        return ResponseEntity.ok(tokenService.login(loginDto));
    }

}
