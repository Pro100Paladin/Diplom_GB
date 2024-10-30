package ru.medvedev.bankservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.medvedev.bankservice.model.Phone;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью Phone.
 */
@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
    /**
     * Получение по номеру телефона.
     *
     * @param phoneNumber данные для поиска.
     * @return объект Optional.
     */
    Optional<Phone> findByPhoneNumber(String phoneNumber);

    /**
     * Удаление по номеру телефона.
     *
     * @param phoneNumber данные для удаления.
     */
    void deleteByPhoneNumber(String phoneNumber);
}
