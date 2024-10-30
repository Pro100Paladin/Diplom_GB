package ru.medvedev.bankservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.medvedev.bankservice.model.Client;
import ru.medvedev.bankservice.service.AuthService;
import ru.medvedev.bankservice.service.ClientService;
import ru.medvedev.bankservice.web.model.JwtRequest;
import ru.medvedev.bankservice.web.model.JwtResponse;
import ru.medvedev.bankservice.web.security.JwtTokenProvider;


/**
 * Сервис аутентификации пользователей.
 * Осуществляет ответ парой токенов
 * в случае успешной аутентификации пользователя.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    /**
     * Поле с объектом для аутентификации пользователя.
     */
    private final AuthenticationManager authenticationManager;
    /**
     * Поле с объектом сервиса для получения пользователя из БД.
     */
    private final ClientService clientService;
    /**
     * Поле с объектом для создания токена.
     */
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Метод Jwt ответа (токенами),
     * при успешной аутентификации пользователя.
     *
     * @param loginRequest запрос на аутентификацию.
     * @return ответ токенами.
     */
    @Override
    public JwtResponse login(final JwtRequest loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));
        Client client = clientService.getByUsername(loginRequest.getUsername());
        jwtResponse.setId(client.getClientId());
        jwtResponse.setUsername(client.getUsername());
        jwtResponse.setAccessToken(
                jwtTokenProvider.createAccessToken(
                        client.getClientId(),
                        client.getUsername()));
        jwtResponse.setRefreshToken(
                jwtTokenProvider.createRefreshToken(
                        client.getClientId(),
                        client.getUsername()));
        return jwtResponse;
    }

    /**
     * Метод производящий обновление пары токенов.
     *
     * @param refreshToken долгоживущий токен
     * @return ответ с парой токенов.
     */
    @Override
    public JwtResponse refresh(final String refreshToken) {
        return jwtTokenProvider.refreshUserToken(refreshToken);
    }
}
