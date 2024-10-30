package ru.medvedev.bankservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.medvedev.bankservice.model.Client;
import ru.medvedev.bankservice.service.BalanceUpdateService;
import ru.medvedev.bankservice.service.ClientService;

import java.math.BigDecimal;
import java.util.List;

/**
 * Сервис увелечения баланса клиентов.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class BalanceUpdateServiceImpl implements BalanceUpdateService {

    /**
     * Сервис для работы с клиентами.
     */
    private final ClientService clientService;

    /**
     * Обновление баланса клиентов. Каждую минуту баланс увеличивается на 5%,
     * но не превышает 207% от начального депозита.
     */
    @Override
    @Scheduled(cron = "0 * * * * *")
    public void updateBalance(){
        List<Client> clients = clientService.getAll();
        for (Client client : clients){
            BigDecimal currentBalance = client.getAccount().getBalance();
            BigDecimal initialDeposit = client.getAccount().getInitialDeposit();
            BigDecimal increasedBalance = currentBalance.multiply(BigDecimal.valueOf(1.05));
            BigDecimal maxBalance = initialDeposit.multiply(BigDecimal.valueOf(2.07));
            if (increasedBalance.compareTo(maxBalance) > 0) {
                client.getAccount().setBalance(maxBalance);
            } else {
                client.getAccount().setBalance(increasedBalance);
            }
        }
        clientService.updateAllClient(clients);
    }
}
