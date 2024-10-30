package ru.medvedev.bankservice.web.mapper;

import org.springframework.stereotype.Service;
import ru.medvedev.bankservice.model.Account;
import ru.medvedev.bankservice.model.Client;
import ru.medvedev.bankservice.model.Email;
import ru.medvedev.bankservice.model.Phone;
import ru.medvedev.bankservice.web.dto.ClientDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис преобразования Dto в Entity и наоборот.
 */
@Service
public class ClientMapperImpl implements ClientMapper{
    /**
     * Преобразование в Dto.
     * @param client объект сущности.
     * @return объект Dto.
     */
    @Override
    public ClientDto toDto(Client client) {
        ClientDto clientDto = new ClientDto();
        clientDto.setUsername(client.getUsername());
        clientDto.setName(client.getName());
        clientDto.setBirthday(client.getBirthday());
        List<String> phones = List.copyOf(client.getPhones()
                .stream()
                .map(Phone::getPhoneNumber)
                .toList());
        clientDto.setPhones(phones);
        List<String> emails = List.copyOf(client.getEmails()
                .stream()
                .map(Email::getEmail)
                .toList());
        clientDto.setEmails(emails);
        clientDto.setBalance(client.getAccount().getBalance());
        return clientDto;
    }

    /**
     * Преобразование в сущность.
     * @param clientDto объект dto.
     * @return объект сущности.
     */
    @Override
    public Client toEntity(ClientDto clientDto) {
        Client client = new Client();
        client.setUsername(clientDto.getUsername());
        client.setPassword(clientDto.getPassword());
        // Возможно стоит убрать todo
        client.setName(clientDto.getName());
        client.setBirthday(clientDto.getBirthday());
        //
        List<Phone> phones = new ArrayList<>();
        for (String phone : clientDto.getPhones()){
            Phone newPhone = new Phone();
            newPhone.setPhoneNumber(phone);
            newPhone.setClient(client);
            phones.add(newPhone);
        }
        client.setPhones(phones);
        List<Email> emails = new ArrayList<>();
        for (String email : clientDto.getEmails()){
            Email newEmail = new Email();
            newEmail.setEmail(email);
            newEmail.setClient(client);
            emails.add(newEmail);
        }
        client.setEmails(emails);
        Account account = new Account();
        account.setBalance(clientDto.getBalance());
        account.setClient(client);
        client.setAccount(account);
        return client;
    }
}
