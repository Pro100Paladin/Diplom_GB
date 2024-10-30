package ru.medvedev.bankservice.model.exception;

/**
 * Некорректная сумма перевода.
 */
public class IncorrectTransferAmountException extends RuntimeException{
    public IncorrectTransferAmountException(String message) {
        super(message);
    }
}
