package ru.medvedev.bankservice.model.exception;

/**
 * Исключение выбрасывается при отсутствии достаточных прав у пользователя.
 */
public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {
        super();
    }

}
