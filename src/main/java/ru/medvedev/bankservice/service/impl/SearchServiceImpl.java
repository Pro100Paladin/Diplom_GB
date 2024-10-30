package ru.medvedev.bankservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.medvedev.bankservice.model.Client;
import ru.medvedev.bankservice.model.exception.ResourceNotFoundException;
import ru.medvedev.bankservice.repository.ClientRepository;
import ru.medvedev.bankservice.service.SearchService;

import java.time.LocalDate;
import java.util.List;

/**
 * Сервис поиска и фильтрации клиентов.
 */
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    /**
     * Репозиторий для работы с клиентами.
     */
    private final ClientRepository clientRepository;

    /**
     * Поиск клиентов старше переданной даты.
     * @param date дата для поиска.
     * @param sort включение сортировки по дате рождения.
     * @return список клиентов.
     */
    public List<Client> findOlderDate(LocalDate date, boolean sort){
        if (sort) {
            return clientRepository.findByBirthdayAfterOrderByBirthday(date);
        }
        return clientRepository.findByBirthdayAfter(date);
    }

    /**
     * Поиск клиентов старше переданной даты.
     * @param date дата для поиска.
     * @param page номер страницы.
     * @param size количество записей.
     * @param sort включение сортировки по дате рождения.
     * @return список клиентов.
     */
    public List<Client> findOlderDate(LocalDate date, int page, int size, boolean sort){
        Pageable pageable = PageRequest.of(page, size);
        if (sort) {
            return clientRepository.findByBirthdayAfterOrderByBirthday(date, pageable);
        }else{
            return clientRepository.findByBirthdayAfter(date, pageable);
        }
    }

    /**
     * Поиск по номеру телефона.
     * @param phone номер для поиска.
     * @return найденный клиент.
     * @throws ResourceNotFoundException клиент не найден.
     */
    public Client findByPhone(String phone) throws ResourceNotFoundException {
        List<Client> findClient = clientRepository.findByPhoneNumber(phone);
        if (findClient.isEmpty()){
            throw new ResourceNotFoundException("Client by phone " + phone + " not found!");
        }
        return findClient.get(0);
    }

    /**
     * Поиск по имени.
     * @param name имя клиента.
     * @param sort включение сортировки по алфавиту.
     * @return список клиентов.
     */
    public List<Client> findByName(String name, boolean sort){
        if (sort) return clientRepository.findByNameLikeOrderByName(name);
        return clientRepository.findByNameLike(name);
    }

    /**
     * Поиск по имени.
     * @param name имя клиента.
     * @param page номер страницы.
     * @param size количество записей.
     * @param sort включение сортировки по алфавиту.
     * @return список клиентов.
     */
    public List<Client> findByName(String name, int page, int size, boolean sort){
        Pageable pageable = PageRequest.of(page, size);
        if (sort) {
            return clientRepository.findByNameLikeOrderByName(name, pageable);
        }
        return clientRepository.findByNameLike(name, pageable);
    }

    /**
     * Поиск по email.
     * @param email email для поиска.
     * @return найденный клиент.
     * @throws ResourceNotFoundException клиент не найден.
     */
    public Client findByEmail(String email) throws ResourceNotFoundException {
        List<Client> findClient = clientRepository.findByEmail(email);
        if (findClient.isEmpty()){
            throw new ResourceNotFoundException("Client by email " + email + " not found!");
        }
        return findClient.get(0);
    }
}
