package ru.medvedev.bankservice.service;

import ru.medvedev.bankservice.model.Client;

import java.util.List;

/**
 * Интерфейс сервиса клиентов.
 */
public interface ClientService {
    /**
     * Получение клиента по логину.
     * @param username логин клиента.
     * @return объект клиента.
     */
    Client getByUsername(String username);

    /**
     * Получение всех клиентов.
     * @return список клиентов.
     */
    List<Client> getAll();

    /**
     * Получение клиента по id.
     * @param clientId идентификатор клиента.
     * @return объект клиента.
     */
    Client getClientById(Long clientId);

    /**
     * Обновление данных клиента.
     * @param client объект клиента для обновления.
     */
    void updateClient(Client client);

    /**
     * Обновление данных списка клиентов.
     * @param clients список клиентов для обновления.
     */
    void updateAllClient(List<Client> clients);

    /**
     * Создание клиента.
     * @param client объект клиента.
     * @return объект созданного клиента.
     */
    Client createClient(Client client);

    /**
     * Добавление нового телефона.
     * @param clientId идентификатор клиента.
     * @param phone номер телефона.
     * @return обновленный объект клиента.
     */
    Client addPhone(Long clientId, String phone);

    /**
     * Добавление нового email.
     * @param clientId идентификатор клиента.
     * @param email электронная почта.
     * @return объект обновленного клиента.
     */
    Client addEmail(Long clientId, String email);

    /**
     * Удаление номера телефона.
     * @param clientId идентификатор клиента.
     * @param phone номер телефона.
     * @return обновленный объект клиента.
     */
    Client removePhone(Long clientId, String phone);

    /**
     * Удаление email.
     * @param clientId идентификатор клиента.
     * @param email электронная почта.
     * @return обновленный объект клиента.
     */
    Client removeEmail(Long clientId, String email);
}
