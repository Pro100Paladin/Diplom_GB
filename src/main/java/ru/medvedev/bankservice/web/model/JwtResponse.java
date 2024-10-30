package ru.medvedev.bankservice.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Класс JWT ответов при аутентификации.
 */
@Data
@Schema(description = "JwtResponse")
public class JwtResponse {
    /**
     * Поле с идентификатором пользователя.
     */
    @Schema(description = "id", example = "1")
    private long id;
    /**
     * Поле с логином пользователя.
     */
    @Schema(description = "username", example = "ivan11")
    private String username;
    /**
     * Поле с короткоживущим токеном.
     */
    @Schema(description = "accessToken", example = "some access token")
    private String accessToken;
    /**
     * Поле с долгоживущим токеном.
     */
    @Schema(description = "refreshToken", example = "some refresh token")
    private String refreshToken;
}
