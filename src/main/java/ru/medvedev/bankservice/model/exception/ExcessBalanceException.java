package ru.medvedev.bankservice.model.exception;

/**
 * Исключение при превышении остатка средств.
 */
public class ExcessBalanceException extends RuntimeException{

    public ExcessBalanceException(String message) {
        super(message);
    }
}
