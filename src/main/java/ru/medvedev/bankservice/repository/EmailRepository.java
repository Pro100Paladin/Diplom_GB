package ru.medvedev.bankservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.medvedev.bankservice.model.Email;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью email.
 */
@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    /**
     * Получение объекта по email.
     *
     * @param email данные для поиска.
     * @return объект Optional.
     */
    Optional<Email> findByEmail(String email);

    /**
     * Удаление по email.
     *
     * @param email данные для удаления.
     */
    void deleteByEmail(String email);
}
