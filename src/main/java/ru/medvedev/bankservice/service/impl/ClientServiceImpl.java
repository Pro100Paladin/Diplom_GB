package ru.medvedev.bankservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.medvedev.bankservice.model.Client;
import ru.medvedev.bankservice.model.Email;
import ru.medvedev.bankservice.model.Phone;
import ru.medvedev.bankservice.model.exception.ClientUpdateException;
import ru.medvedev.bankservice.model.exception.ResourceNotFoundException;
import ru.medvedev.bankservice.repository.ClientRepository;
import ru.medvedev.bankservice.repository.EmailRepository;
import ru.medvedev.bankservice.repository.PhoneRepository;
import ru.medvedev.bankservice.service.ClientService;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Сервис для работы с клиентами.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {
    /**
     * Репозиторий клиента.
     */
    private final ClientRepository clientRepository;
    /**
     * Репозиторий телефона.
     */
    private final PhoneRepository phoneRepository;
    /**
     * Репозиторий email.
     */
    private final EmailRepository emailRepository;
    /**
     * Кодировщик паролей.
     */
    private final PasswordEncoder passwordEncoder;
    /**
     * Выражение проверки email.
     */
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    /**
     * Выражение проверки телефона.
     */
    private static final String PHONE_REGEX = "^\\+7-(\\d{3}-){2}\\d{2}-\\d{2}$";
    /**
     * Получение клиента по логину.
     * @param username логин клиента.
     * @return объект клиента.
     * @throws ResourceNotFoundException отсутствие клиента.
     */
    @Override
    public Client getByUsername(String username) throws ResourceNotFoundException {
        return clientRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException("User by " + username + " not found!"));
    }

    /**
     * Получение всех клиентов.
     * @return список клиентов.
     */
    @Override
    public List<Client> getAll(){
        return clientRepository.findAll();
    }

    /**
     * Получение клиента по id.
     * @param clientId идентификатор клиента.
     * @return объект клиента.
     * @throws ResourceNotFoundException отсутствие клиента.
     */
    @Override
    public Client getClientById(Long clientId) throws ResourceNotFoundException{
        Optional<Client> client = clientRepository.findById(clientId);
        return client.orElseThrow(() ->
                new ResourceNotFoundException("client by " + clientId + " not found!"));
    }

    /**
     * Обновление данных клиента.
     * @param client объект клиента для обновления.
     */
    @Override
    @Transactional
    public void updateClient(Client client){
        clientRepository.save(client);
    }

    /**
     * Обновление данных списка клиентов.
     * @param clients список клиентов для обновления.
     */
    @Override
    @Transactional
    public void updateAllClient(List<Client> clients){
        clientRepository.saveAll(clients);
    }

    /**
     * Создание клиента.
     * @param client объект клиента.
     * @return объект созданного клиента.
     * @throws ClientUpdateException логин занят.
     */
    @Override
    @Transactional
    public Client createClient(Client client) throws ClientUpdateException{
        Optional<Client> checkUsername =
                clientRepository.findByUsername(client.getUsername());
        if (checkUsername.isPresent()){
            throw new ClientUpdateException("Username " + client.getUsername() + " is busy");
        }
        client.getPhones().forEach(p -> checkPhone(p.getPhoneNumber()));
        client.getEmails().forEach(e -> checkEmail(e.getEmail()));
        client.getAccount().setInitialDeposit(client.getAccount().getBalance());
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        return clientRepository.save(client);
    }

    /**
     * Добавление нового телефона.
     * @param clientId идентификатор клиента.
     * @param phone номер телефона.
     * @return обновленный объект клиента.
     */
    @Override
    @Transactional
    public Client addPhone(Long clientId, String phone){
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(phone);
        if (!matcher.matches()) {
            throw new ClientUpdateException("Invalid phone format: " + phone);
        }
        checkPhone(phone);
        Client client = getClientById(clientId);
        Phone newPhone = new Phone();
        newPhone.setPhoneNumber(phone);
        newPhone.setClient(client);
        client.getPhones().add(newPhone);
        return clientRepository.save(client);
    }

    /**
     * Добавление нового email.
     * @param clientId идентификатор клиента.
     * @param email электронная почта.
     * @return объект обновленного клиента.
     */
    @Override
    @Transactional
    public Client addEmail(Long clientId, String email){
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new ClientUpdateException("Invalid email format: " + email);
        }
        checkEmail(email);
        Client client = getClientById(clientId);
        Email newEmail = new Email();
        newEmail.setEmail(email);
        newEmail.setClient(client);
        client.getEmails().add(newEmail);
        return clientRepository.save(client);
    }

    /**
     * Удаление номера телефона.
     * @param clientId идентификатор клиента.
     * @param phone номер телефона.
     * @return обновленный объект клиента.
     * @throws ClientUpdateException ошибки при удалении номера телефона.
     */
    @Override
    @Transactional
    public Client removePhone(Long clientId, String phone) throws ClientUpdateException{
        Client client = getClientById(clientId);
        boolean phoneExists = client.getPhones()
                .stream()
                .anyMatch(p -> p.getPhoneNumber().equals(phone));

        if (!phoneExists){
            throw new ClientUpdateException("Phone " + phone + " not found!");
        }

        if (client.getPhones().size() == 1){
            throw new ClientUpdateException("You can't delete the last number!");
        }
        client.getPhones().removeIf(p -> p.getPhoneNumber().equals(phone));
        phoneRepository.deleteByPhoneNumber(phone);

        return clientRepository.save(client);
    }

    /**
     * Удаление email.
     * @param clientId идентификатор клиента.
     * @param email электронная почта.
     * @return обновленный объект клиента.
     * @throws ClientUpdateException ошибки при удалении email.
     */
    @Override
    @Transactional
    public Client removeEmail(Long clientId, String email) throws ClientUpdateException{
        Client client = getClientById(clientId);
        boolean emailExists = client.getEmails()
                .stream()
                .anyMatch(e -> e.getEmail().equals(email));

        if (!emailExists){
            throw new ClientUpdateException("Email " + email + " not found!");
        }

        if (client.getEmails().size() == 1){
            throw new ClientUpdateException("You can't delete the last email!");
        }

        client.getEmails().removeIf(e -> e.getEmail().equals(email));
        emailRepository.deleteByEmail(email);

        return clientRepository.save(client);
    }

    /**
     * Служебный метод проверки существования номера телефона в БД.
     * @param phone номер телефона для проверки.
     * @throws ClientUpdateException номер телефона занят.
     */
    private void checkPhone(String phone) throws ClientUpdateException{
        Optional<Phone> findPhone = phoneRepository.findByPhoneNumber(phone);
        if (findPhone.isPresent()){
            throw new ClientUpdateException("Phone " + findPhone.get().getPhoneNumber() + " is busy!");
        }
    }

    /**
     * Служебный метод проверки существования email в БД.
     * @param email email для проверки.
     * @throws ClientUpdateException email занят.
     */
    private void checkEmail(String email) throws ClientUpdateException{
        Optional<Email> findEmail = emailRepository.findByEmail(email);
        if (findEmail.isPresent()){
            throw new ClientUpdateException("Email " + findEmail.get().getEmail() + " is busy!");
        }
    }

}
