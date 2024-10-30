package ru.medvedev.bankservice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

/**
 * Сущность клиента.
 */
@Data
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;
    private String username;
    private String password;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    @OneToMany(mappedBy = "client",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Phone> phones;

    @OneToMany(mappedBy = "client",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Email> emails;
    @OneToOne(mappedBy = "client",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Account account;
}
