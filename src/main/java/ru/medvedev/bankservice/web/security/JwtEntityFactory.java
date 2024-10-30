package ru.medvedev.bankservice.web.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.medvedev.bankservice.model.Client;

import java.util.Collections;

/**
 * Класс преобразующий объект модели клиента (Client) в объект
 * проверяемый Spring Security на предмет авторизации в приложении.
 */
public class JwtEntityFactory {

    /**
     * Метод преобразующий объект модели в JwtEntity,
     * т.е. тот объект, который будет проверять Spring Security.
     *
     * @param client объект модели.
     * @return объект проверки для Spring Security.
     */
    public static JwtEntity create(final Client client) {
        return new JwtEntity(
                client.getClientId(),
                client.getUsername(),
                client.getName(),
                client.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
