package com.example.csservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) для представления ответа аутентификации с использованием JWT.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {

    private String accessToken;
    /**
     * Токен JWT, выданный при успешной аутентификации.
     */
    private String refreshToken;

    /**
     * Уникальный идентификатор пользователя, которому принадлежит токен.
     */
    private Long userId;

}
