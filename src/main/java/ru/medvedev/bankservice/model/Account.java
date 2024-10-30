package ru.medvedev.bankservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Сущность аккаунта.
 */
@Data
@Entity
@Table(name = "bank_account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;
    /**
     * Текущий баланс.
     */
    private BigDecimal balance;
    /**
     * Изначальный депозит.
     */
    private BigDecimal initialDeposit;

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", balance=" + balance +
                '}';
    }
}
