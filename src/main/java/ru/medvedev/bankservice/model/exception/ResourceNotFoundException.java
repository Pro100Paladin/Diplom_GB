package ru.medvedev.bankservice.model.exception;

/**
 * Отсутствие объекта при запросе к БД.
 */
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
