package com.example.csservice.controller;


import com.example.csservice.dto.JwtAuthenticationResponse;
import com.example.csservice.dto.SignInRequest;
import com.example.csservice.dto.SignUpRequest;
import com.example.csservice.services.impl.AuthenticationServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "Управление аутентификацией пользователей")
public class AuthController {
    private final AuthenticationServiceImpl authenticationService;

    @Operation(summary = "Регистрация пользователя", description = "Создает нового пользователя и возвращает JWT токен (доступно только администратору).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован",
                    content = @Content(schema = @Schema(implementation = JwtAuthenticationResponse.class))),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав"),
            @ApiResponse(responseCode = "400", description = "Ошибка регистрации")
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")  // ✅ Только админ может регистрировать новых пользователей
    @PostMapping("/sign-up")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signUp(request));
    }

    @Operation(summary = "Вход пользователя", description = "Аутентифицирует пользователя и возвращает JWT токен.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно аутентифицирован",
                    content = @Content(schema = @Schema(implementation = JwtAuthenticationResponse.class))),
            @ApiResponse(responseCode = "401", description = "Неверные учетные данные")
    })
    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }
}