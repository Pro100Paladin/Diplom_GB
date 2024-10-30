package ru.medvedev.bankservice.service;

import java.math.BigDecimal;

/**
 * Интерфейс сервиса банковских операций.
 */
public interface TransferService {
    /**
     *
     * Перевод средств от одного клиента к другому.
     * @param creditClientId клиент плотильщик.
     * @param debitClientId клиент получатель.
     * @param sum сумма перевода.
     */
    void transfer(Long creditClientId, Long debitClientId, BigDecimal sum);
}
