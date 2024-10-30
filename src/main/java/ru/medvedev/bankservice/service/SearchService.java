package ru.medvedev.bankservice.service;


import ru.medvedev.bankservice.model.Client;
import ru.medvedev.bankservice.model.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;

/**
 * Интерфейс сервиса поиска и фильтрации клиентов.
 */
public interface SearchService {
    /**
     * Поиск клиентов старше переданной даты.
     * @param date дата для поиска.
     * @param sort включение сортировки по дате рождения.
     * @return список клиентов.
     */
    List<Client> findOlderDate(LocalDate date, boolean sort);

    /**
     * Поиск клиентов старше переданной даты.
     * @param date дата для поиска.
     * @param page номер страницы.
     * @param size количество записей.
     * @param sort включение сортировки по дате рождения.
     * @return список клиентов.
     */
    List<Client> findOlderDate(LocalDate date, int page, int size, boolean sort);

    /**
     * Поиск по номеру телефона.
     * @param phone номер для поиска.
     * @return найденный клиент.
     * @throws ResourceNotFoundException клиент не найден.
     */
    Client findByPhone(String phone);

    /**
     * Поиск по имени.
     * @param name имя клиента.
     * @param sort включение сортировки по алфавиту.
     * @return список клиентов.
     */
    List<Client> findByName(String name, boolean sort);

    /**
     * Поиск по имени.
     * @param name имя клиента.
     * @param page номер страницы.
     * @param size количество записей.
     * @param sort включение сортировки по алфавиту.
     * @return список клиентов.
     */
    List<Client> findByName(String name, int page, int size, boolean sort);

    /**
     * Поиск по email.
     * @param email email для поиска.
     * @return найденный клиент.
     */
    Client findByEmail(String email);

}
