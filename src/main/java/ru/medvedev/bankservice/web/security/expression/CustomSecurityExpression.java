package ru.medvedev.bankservice.web.security.expression;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.medvedev.bankservice.web.security.JwtEntity;

/**
 * Класс проверки прав доступа к эндпоинтам.
 */
@Service("customSecurityExpression")
@RequiredArgsConstructor
public class CustomSecurityExpression {
    /**
     * Проверка соответствия id.
     * @param id идентификатор пользователя.
     * @return true - соответствие, иначе false.
     */
    public boolean canAccessUser(final Long id) {
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();

        JwtEntity client = (JwtEntity) authentication.getPrincipal();

        Long clientId = client.getId();

        return clientId.equals(id);
    }
}
