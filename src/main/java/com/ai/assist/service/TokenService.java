package com.ai.assist.service;

import com.ai.assist.dto.LoginDto;
import com.ai.assist.dto.response.LoginResponse;

public interface TokenService {

    LoginResponse login(LoginDto loginDto);

}
