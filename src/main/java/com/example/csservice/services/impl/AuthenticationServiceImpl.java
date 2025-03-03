package com.example.csservice.services.impl;

import com.example.csservice.dto.JwtAuthenticationResponse;
import com.example.csservice.dto.RefreshTokenRequest;
import com.example.csservice.dto.SignInRequest;
import com.example.csservice.dto.SignUpRequest;
import com.example.csservice.entity.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private final UserServiceImpl userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRoles());
        userService.create(user);

        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return new JwtAuthenticationResponse(accessToken, refreshToken, userService.getByUsername(user.getUsername()).getId());
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()
        ));

        var user = userService.userDetailsService().loadUserByUsername(request.getUsername());

        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return new JwtAuthenticationResponse(accessToken, refreshToken, userService.getByUsername(request.getUsername()).getId());
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest request) {
        String username = jwtService.extractUserName(request.refreshToken());

        if (username == null) {
            throw new RuntimeException("Недействительный рефреш токен ");
        }

        var user = userService.userDetailsService().loadUserByUsername(username);

        if (!jwtService.isTokenValid(request.refreshToken(), user)) {
            throw new RuntimeException("Недействительный или просроченный рефреш токен");
        }

        var newAccessToken = jwtService.generateToken(user);

        return new JwtAuthenticationResponse(newAccessToken, request.refreshToken(), userService.getByUsername(username).getId());
    }
}