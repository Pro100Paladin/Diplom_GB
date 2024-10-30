package ru.medvedev.bankservice.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.medvedev.bankservice.service.AuthService;
import ru.medvedev.bankservice.web.model.JwtRequest;
import ru.medvedev.bankservice.web.model.JwtResponse;


/**
 * Контроллер отвечающий за авторизацию пользователей.
 */

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller", description = "Auth API")
public class AuthController {
    /**
     * Поле сервиса аутентификации.
     */
    private final AuthService authService;

    /**
     * Аутентификация пользователя.
     *
     * @param loginRequest принимает запрос пользователя
     *                     в виде jwt запроса (с токеном)
     * @return jwt ответ с парой токенов.
     */
    @PostMapping("/login")
    @Operation(summary = "Authenticate client")
    public JwtResponse login(
            @Validated @RequestBody final JwtRequest loginRequest) {
        return authService.login(loginRequest);
    }

    /**
     * Метод обновления пары токенов.
     *
     * @param refreshToken токен для обновления.
     * @return обновленные токены.
     */
    @Operation(summary = "Refresh token")
    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody final String refreshToken) {
        return authService.refresh(refreshToken);
    }
}
