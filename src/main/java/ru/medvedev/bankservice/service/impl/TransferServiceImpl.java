package ru.medvedev.bankservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.medvedev.bankservice.model.Client;
import ru.medvedev.bankservice.model.exception.ExcessBalanceException;
import ru.medvedev.bankservice.model.exception.IncorrectTransferAmountException;
import ru.medvedev.bankservice.service.ClientService;
import ru.medvedev.bankservice.service.TransferService;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Сервис банковских операций.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    /**
     * Сервис клиентов.
     */
    private final ClientService clientService;
    /**
     * Объект блокировки.
     */
    private final Lock lock = new ReentrantLock();

    /**
     * Перевод средств от одного клиента к другому.
     * @param creditClientId клиент плотильщик.
     * @param debitClientId клиент получатель.
     * @param sum сумма перевода.
     * @throws IncorrectTransferAmountException некорректная сумма перевода.
     * @throws ExcessBalanceException превышение баланса клиента плотильщика.
     */
    public void transfer(Long creditClientId, Long debitClientId, BigDecimal sum)
            throws IncorrectTransferAmountException, ExcessBalanceException {
        if (sum.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IncorrectTransferAmountException("Incorrect transfer amount!");
        }
        Client creditClient = clientService.getClientById(creditClientId);
        BigDecimal creditBalance = creditClient.getAccount().getBalance();
        if (creditBalance.compareTo(sum) < 0) {
            throw new ExcessBalanceException("Exceeding account balance!");
        }
        Client debitClient = clientService.getClientById(debitClientId);
        BigDecimal debitBalance = debitClient.getAccount().getBalance();
        try {
            lock.lock();
            creditClient.getAccount().setBalance(creditBalance.subtract(sum));
            debitClient.getAccount().setBalance(debitBalance.add(sum));
            clientService.updateClient(creditClient);
            clientService.updateClient(debitClient);
        } finally {
            lock.unlock();
        }
    }
}
