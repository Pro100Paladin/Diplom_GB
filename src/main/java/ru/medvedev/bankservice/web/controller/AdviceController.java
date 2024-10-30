package ru.medvedev.bankservice.web.controller;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.medvedev.bankservice.model.exception.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер Обработки исключений.
 */
@Slf4j
@RestControllerAdvice
public class AdviceController {

    /**
     * Эндпоинт обработки исключений отсутствия объектов.
     *
     * @param e объект исключения.
     * @return ответ с сообщением об ошибках.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleResourceNotFound(ResourceNotFoundException e) {
        log.warn(e.getMessage());
        return new ExceptionBody(e.getMessage());
    }
    /**
     * Эндпоинт обработки исключений обновления клиентов.
     *
     * @param e объект исключения.
     * @return ответ с сообщением об ошибках.
     */
    @ExceptionHandler(ClientUpdateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleClientUpdate(ClientUpdateException e) {
        log.warn(e.getMessage());
        return new ExceptionBody(e.getMessage());
    }
    /**
     * Эндпоинт обработки исключений превышения баланса клиента.
     *
     * @param e объект исключения.
     * @return ответ с сообщением об ошибках.
     */
    @ExceptionHandler(ExcessBalanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleExcessBalance(ExcessBalanceException e) {
        log.warn(e.getMessage());
        return new ExceptionBody(e.getMessage());
    }
    /**
     * Эндпоинт обработки исключений некорректной суммы перевода.
     *
     * @param e объект исключения.
     * @return ответ с сообщением об ошибках.
     */
    @ExceptionHandler(IncorrectTransferAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleIncorrectTransferAmount(
            IncorrectTransferAmountException e) {
        log.warn(e.getMessage());
        return new ExceptionBody(e.getMessage());
    }

    /**
     * Эндпоинт обработки исключений валидаций объектов.
     *
     * @param e объект исключения.
     * @return ответ с сообщением об ошибках.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleMethodArgumentNotValid(
            MethodArgumentNotValidException e) {
        log.warn(e.getMessage());
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed!");
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        exceptionBody.setErrors(
                errors.stream()
                        .collect(
                                Collectors.toMap(
                                        FieldError::getField,
                                        FieldError::getDefaultMessage)
                        )
        );
        return exceptionBody;
    }
    /**
     * Исключение при ошибках аутентификации,
     * неверном токене.
     *
     * @return объект ExceptionBody
     */
    @ExceptionHandler({AccessDeniedException.class,
            org.springframework.security.access.AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleAccessDenied() {
        return new ExceptionBody("Access denied.");
    }

    /**
     * Исключение при просроченном токене.
     * @param e объект исключения.
     * @return объект ExceptionBody.
     */
    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleExpiredJwt(ExpiredJwtException e) {
        log.warn(e.getMessage());
        return new ExceptionBody("Access denied. " + e.getMessage());
    }
}
