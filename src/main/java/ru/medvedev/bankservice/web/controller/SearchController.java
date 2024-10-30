package ru.medvedev.bankservice.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.medvedev.bankservice.model.Client;
import ru.medvedev.bankservice.service.impl.SearchServiceImpl;
import ru.medvedev.bankservice.web.dto.ClientDto;
import ru.medvedev.bankservice.web.mapper.ClientMapper;

import java.time.LocalDate;
import java.util.List;

/**
 * Контроллер api поиска и сортировки клиентов.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
@Tag(name = "Search Controller", description = "Search API")
public class SearchController {
    /**
     * Сервис поиска и сортировки.
     */
    private final SearchServiceImpl searchService;
    /**
     * Преобразование DTO в Entity и наоборот.
     */
    private final ClientMapper clientMapper;

    /**
     * Энпоинт поиска клиентов старше переданной даты рождения.
     * @param date дата рождения.
     * @param page номер страницы
     * @param size размер страницы
     * @param sort включение сортировки по дате рождения.
     * @return ответ со списком клиентов.
     */
    @GetMapping("/birthday")
    @Operation(summary = "Search for clients from a specified date of birth")
    public ResponseEntity<List<ClientDto>> getByBirthday(
            @RequestParam("date") LocalDate date,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "sort_by_year", required = false) boolean sort){
        List<Client> findClients;
        if (page == null && size == null){
            findClients = searchService.findOlderDate(date, sort);
        }else{
            findClients = searchService.findOlderDate(date, page, size, sort);
        }
        return ResponseEntity.ok(findClients.stream().map(clientMapper::toDto).toList());
    }

    /**
     * Энпоинт поиска клиентов по номеру телефона.
     * @param phone номер телефона.
     * @return ответ с объектом клиента.
     */
    @GetMapping("/phone")
    @Operation(summary = "Search for clients from a phone")
    public ResponseEntity<ClientDto> getByPhone(
            @RequestParam String phone){
        return ResponseEntity.ok(
                clientMapper.toDto(searchService.findByPhone(phone)));
    }

    /**
     * Энпоинт поиска клиентов по имени.
     * @param name имя клиента.
     * @param page номер страницы.
     * @param size размер страницы.
     * @param sort включение сортировки по алфавиту.
     * @return ответ со списком клиентов.
     */
    @GetMapping("/name")
    @Operation(summary = "Search for clients from a name")
    public ResponseEntity<List<ClientDto>> getByName(
            @RequestParam String name,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "sort_by_name", required = false) boolean sort){
        List<Client> findClients;
        name = name + "%";
        if (page == null && size == null){
            findClients = searchService.findByName(name, sort);
        }else{
            findClients = searchService.findByName(name, page, size, sort);
        }
        return ResponseEntity.ok(findClients
                .stream().map(clientMapper::toDto).toList());
    }

    /**
     * Энпоинт поиска клиентов по email.
     * @param email адрес email.
     * @return ответ с объектом клиента.
     */
    @GetMapping("/email")
    @Operation(summary = "Search for clients from a email")
    public ResponseEntity<ClientDto> getByEmail(@RequestParam String email){
        return ResponseEntity.ok(
                clientMapper.toDto(searchService.findByEmail(email)));
    }
}
