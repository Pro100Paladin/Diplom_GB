package ru.medvedev.bankservice.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.medvedev.bankservice.model.Client;
import ru.medvedev.bankservice.model.exception.AccessDeniedException;
import ru.medvedev.bankservice.service.ClientService;
import ru.medvedev.bankservice.web.model.JwtResponse;
import ru.medvedev.bankservice.web.security.props.JwtProperties;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;


/**
 * Сервисный класс обеспечивающий работу с токенами.
 * Их создание, проверку.
 */
@Service
@RequiredArgsConstructor
public class JwtTokenProvider {
    /**
     * Поле с данными токена полученными из application.yaml.
     */
    private final JwtProperties jwtProperties;
    /**
     * +
     * Поле с сервисом авторизации пользователя.
     */
    private final UserDetailsService userDetailsService;
    /**
     * Поле с сервисом для работы с БД объекта пользователя.
     */
    private final ClientService clientService;
    /**
     * Поле с секретным ключом токенов.
     * Заполняется в конструкторе из зависимостей в application.yaml
     */
    private Key key;

    /**
     * Заполнение поля ключа. В поле присваивается объект Keys
     * в который вносится секретный ключ из application.yaml
     * Используются библиотеки jjwt.
     */
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    /**
     * Метод создания access (короткоживущего) токена.
     * Короткоживущий токен используется
     * для аутентификации и авторизации пользователя.
     *
     * @param clientId   идентификатор пользователя.
     * @param username логин пользователя.
     * @return строку в access токеном.
     */
    public String createAccessToken(
            final Long clientId,
            final String username) {
        Claims claims = Jwts.claims()
                .subject(username)
                .add("id", clientId)
                .add("roles", Set.of("ROLE_USER"))
                .build();

        Instant validity = Instant.now()
                .plus(jwtProperties.getAccess(), ChronoUnit.HOURS);
        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    /**
     * Создание refresh (долгоживущего) токена.
     *
     * @param clientId   идентификатор пользователя.
     * @param username логин пользователя.
     * @return долгоживущий токен.
     */
    public String createRefreshToken(
            final Long clientId,
            final String username) {
        Claims claims = Jwts.claims()
                .subject(username).add("id", clientId).build();
        Instant validity = Instant.now()
                .plus(jwtProperties.getAccess(), ChronoUnit.DAYS);
        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    /**
     * Метод получающий refresh токен, производящий его валидацию,
     * если валидация успешна, для пользователя обновляется пара токенов
     * и отправляется обратно.
     *
     * @param refreshToken долгоживущий токен.
     * @return jwt ответ с парой токенов.
     */
    public JwtResponse refreshUserToken(final String refreshToken) {
        JwtResponse jwtResponse = new JwtResponse();
        if (!validateToken(refreshToken)) {
            throw new AccessDeniedException();
        }
        Long clientId = Long.parseLong(getId(refreshToken));
        Client client = clientService.getClientById(clientId);
        jwtResponse.setId(clientId);
        jwtResponse.setUsername(client.getUsername());
        jwtResponse.setAccessToken(
                createAccessToken(
                        clientId,
                        client.getUsername()));
        jwtResponse.setRefreshToken(
                createRefreshToken(
                        clientId,
                        client.getUsername()));
        return jwtResponse;
    }

    /**
     * Метод производящий валидацию токенов.
     * (Обрабатывает как access, так и refresh токены)
     *
     * @param token токен в строковом представлении.
     * @return true при успешной валидации, иначе false.
     */
    public boolean validateToken(final String token) {
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseSignedClaims(token);
        return !claims.getPayload().getExpiration().before(new Date());
    }

    /**
     * Метод получения Id клиента из токена.
     *
     * @param token токен в строковом представлении
     * @return Id пользователя в строковом представлении
     */
    private String getId(final String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id")
                .toString();
    }

    /**
     * Метод прохождения клиентом аутентификации.
     *
     * @param token токен в строковом представлении.
     * @return объект аутентификации.
     */
    public Authentication getAuthentication(final String token) {
        String username = getUsername(token);
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities());
    }

    /**
     * Метод получения логина пользователя из токена.
     *
     * @param token токен в строковом представлении.
     * @return логин пользователя в строковом представлении.
     */
    private String getUsername(final String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
