package ru.medvedev.bankservice.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.medvedev.bankservice.model.Client;
import ru.medvedev.bankservice.service.impl.ClientServiceImpl;
import ru.medvedev.bankservice.web.dto.ClientDto;
import ru.medvedev.bankservice.web.mapper.ClientMapper;

/**
 * Служебный контроллер.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Validated
@Tag(name = "Admin Controller", description = "Admin API")
public class AdminController {

    private final ClientServiceImpl clientService;
    private final ClientMapper clientMapper;

    /**
     * Эндпоинт создания пользователей.
     * @param clientDto объект клиента.
     * @return ответ с новым клиентом.
     */
    @PostMapping("/create")
    @Operation(summary = "Create new client")
    public ResponseEntity<ClientDto> createClient(
            @Validated @RequestBody ClientDto clientDto){
        Client client = clientService.createClient(clientMapper.toEntity(clientDto));
        log.warn("Create new user {}", client.getUsername());
        return ResponseEntity.ok(clientMapper.toDto(client));
    }

}
