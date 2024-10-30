package ru.medvedev.bankservice.web.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.medvedev.bankservice.model.Client;
import ru.medvedev.bankservice.service.ClientService;

/**
 * Сервисный класс осуществляющий работу с UserService,
 * получающий пользователя из БД,
 * и предающий Spring Security преобразованного полученного
 * пользователя для проведения аутентификации.
 */

@Service
@RequiredArgsConstructor
public class JwtUserDetailService implements UserDetailsService {
    /**
     * Поле сервиса UserService для работы с БД.
     */
    private final ClientService clientService;

    /**
     * Реализованный метод интерфейса UserDetailsService,
     * используя UserService осуществляет запрос в БД для поиска
     * пользователя по логину переданному в параметр метода.
     * Используя маппер преобразует полученный объект пользователя в тип
     * необходимый для Spring Security.
     *
     * @param username логин пользователя.
     * @return преобразованный объект.
     * @throws UsernameNotFoundException генерируется
     * в случае отсутствия пользователя в БД.
     */
    @Override
    public UserDetails loadUserByUsername(
            final String username)
            throws UsernameNotFoundException {
        Client user = clientService.getByUsername(username);
        return JwtEntityFactory.create(user);
    }
}
