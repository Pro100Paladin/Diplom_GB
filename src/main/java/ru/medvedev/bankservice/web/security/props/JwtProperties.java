package ru.medvedev.bankservice.web.security.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Класс хранящий зависимости для токена.
 */
@Component
@Data
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {
    /**
     * Секретный ключ для подписи токена.
     */
    private String secret;
    /**
     * Время жизни access токена.
     */
    private Long access;
    /**
     * Время жизни refresh токена.
     */
    private Long refresh;
}
