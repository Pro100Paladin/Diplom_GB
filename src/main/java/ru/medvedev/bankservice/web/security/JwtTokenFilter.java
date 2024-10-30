package ru.medvedev.bankservice.web.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import ru.medvedev.bankservice.model.exception.ResourceNotFoundException;

import java.io.IOException;

/**
 * Собственный фильтр проводящий аутентификацию
 * пользователя по-передаваемому токену.
 */
@AllArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Метод работы фильтра. Проводит аутентификацию пользователя.
     *
     * @param servletRequest запрос пользователя.
     * @param servletResponse ответ пользователю.
     * @param filterChain фильтр безопасности.
     * @throws IOException исключение
     * @throws ServletException исключение
     */
    @Override
    public void doFilter(
            final ServletRequest servletRequest,
            final ServletResponse servletResponse,
            final FilterChain filterChain)
            throws IOException, ServletException {

        String bearerToken = ((HttpServletRequest) servletRequest)
                .getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7);
        }
        if (bearerToken != null
                && jwtTokenProvider.validateToken(bearerToken)) {
            try {
                Authentication authentication =
                        jwtTokenProvider.getAuthentication(bearerToken);
                if (authentication != null) {
                    SecurityContextHolder
                            .getContext().setAuthentication(authentication);
                }
            } catch (ResourceNotFoundException ignored) {
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
