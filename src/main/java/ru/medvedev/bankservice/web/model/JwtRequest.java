package ru.medvedev.bankservice.web.model;

//import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Класс авторизации пользователя.
 */
@Data
@Schema(description = "Request for login")
public class JwtRequest {
    /**
     * Поле с логином пользователя.
     */
    @NotNull(message = "Логин не может быть пусты!")
    @Schema(description = "username", example = "ivan11")
    private String username;

    /**
     * Поле с паролем пользователя.
     */
    @NotNull(message = "Пароль не может быть пустым!")
    @Schema(description = "password", example = "123")
    private String password;
}
