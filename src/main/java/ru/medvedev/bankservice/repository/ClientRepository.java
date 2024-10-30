package ru.medvedev.bankservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.medvedev.bankservice.model.Client;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностью клиента.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    /**
     * Получение клиента по логину.
     * @param username логин клинта.
     * @return объект Optional.
     */
    Optional<Client> findByUsername(String username);

    /**
     * Получение клиентов старше переданной даты рождения.
     * @param date дата рождения.
     * @return список клиентов.
     */
    List<Client> findByBirthdayAfter(LocalDate date);

    /**
     * Получение клиентов старше переданной даты рождения, с пагинацией.
     * @param date дата рождения.
     * @param pageable объект пагинации.
     * @return список клиентов, с заданной пагинацией.
     */
    List<Client> findByBirthdayAfter(LocalDate date, Pageable pageable);

    /**
     * Получение клиентов старше переданной даты рождения, с сортировкой по дате.
     * @param date дата рождения.
     * @return отсортированный список клиентов.
     */
    List<Client> findByBirthdayAfterOrderByBirthday(LocalDate date);

    /**
     * Получение клиентов старше переданной даты рождения, с сортировкой по дате
     * и пагинацией.
     * @param date дата рождения.
     * @param pageable объект пагинации.
     * @return отсортированный список клиентов с заданной пагинацией.
     */
    List<Client> findByBirthdayAfterOrderByBirthday(LocalDate date, Pageable pageable);

    /**
     * Получение клиентов по номеру телефона.
     * @param phone номер телефона.
     * @return список клиентов.
     */
    @Query("""
            SELECT DISTINCT c FROM Client c
            INNER JOIN c.phones p
            INNER JOIN c.account a
            WHERE p.phoneNumber = :phone
            """)
    List<Client> findByPhoneNumber(@Param("phone") String phone);

    /**
     * Получение клиентов по ФИО.
     * @param name данные для поиска.
     * @return список клиентов.
     */
    List<Client> findByNameLike(String name);

    /**
     * Получение клиентов по ФИО, с сортировкой по алфавиту.
     * @param name данные для поиска.
     * @return отсортированный список клиентов.
     */
    List<Client> findByNameLikeOrderByName(String name);

    /**
     * Получение клиентов по ФИО, с пагинацией.
     * @param name данный для поиска
     * @param pageable объект пагинации.
     * @return список клиентов с условиями пагинации.
     */
    List<Client> findByNameLike(String name, Pageable pageable);

    /**
     * Получение клиентов по ФИО, с сортировкой и пагинацией.
     * @param name данные для поиска.
     * @param pageable объект пагинации.
     * @return отсортированный список клиентов с условиями пагинации.
     */
    List<Client> findByNameLikeOrderByName(String name, Pageable pageable);

    /**
     * Получение клиента по email.
     * @param email данные для поиска.
     * @return список клиентов.
     */
    @Query("""
            SELECT DISTINCT c FROM Client c
            INNER JOIN c.emails e
            INNER JOIN c.account a
            WHERE e.email = :email
            """)
    List<Client> findByEmail(@Param("email") String email);
}
