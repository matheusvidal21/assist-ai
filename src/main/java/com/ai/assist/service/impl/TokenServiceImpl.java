package com.ai.assist.service.impl;

import com.ai.assist.dto.LoginDto;
import com.ai.assist.dto.response.LoginResponse;
import com.ai.assist.model.Role;
import com.ai.assist.service.TokenService;
import com.ai.assist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private UserService userService;

    @Value("${jwt.expiration-second}")
    private Long expirationSecond;

    @Override
    public LoginResponse login(LoginDto loginDto){
        var user = userService.findByUsername(loginDto.getUsername());

        if (user.isEmpty() || !userService.isLoginValid(loginDto, user.get())){
            throw new BadCredentialsException("Invalid username or password");
        }

        var scopes = user.get().getRoles()
                .stream().map(Role::getName).collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("assist-ai")
                .subject(user.get().getId().toString())
                .expiresAt(Instant.now().plusSeconds(expirationSecond))
                .issuedAt(Instant.now())
                .claim("scope", scopes)
                .build();

        String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return LoginResponse.builder()
                .accessToken(jwtValue)
                .expiresIn(expirationSecond)
                .build();
    }


}
