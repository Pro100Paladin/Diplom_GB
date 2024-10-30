package ru.medvedev.bankservice.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.medvedev.bankservice.model.Client;
import ru.medvedev.bankservice.service.ClientService;
import ru.medvedev.bankservice.web.dto.ClientDto;
import ru.medvedev.bankservice.web.mapper.ClientMapper;
import ru.medvedev.bankservice.web.model.ContactRequest;

import java.util.List;

/**
 * Контроллер api клиентов.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/client")
@Tag(name = "Client Controller", description = "Client API")
public class ClientController {
    /**
     * Сервис клиентов.
     */
    private final ClientService clientService;
    /**
     * Преобразование DTO в Entity и наоборот.
     */
    private final ClientMapper clientMapper;

    /**
     * Энпоинт получения всех клиентов.
     * @return ответ со списком клиентов.
     */
    @GetMapping("/")
    @Operation(summary = "Get all clients")
    public ResponseEntity<List<ClientDto>> getClients(){
        return ResponseEntity.ok(
                clientService.getAll()
                        .stream()
                        .map(clientMapper::toDto)
                        .toList());
    }

    /**
     * Энпоинт получения клиента по id.
     * @param clientId идентификатор пользователя.
     * @return ответ с найденным клиентом.
     */
    @GetMapping("/{client_id}")
    @Operation(summary = "Get client by id")
    public ResponseEntity<ClientDto> getClientById(
            @PathVariable("client_id") Long clientId){
        return ResponseEntity.ok(
                clientMapper.toDto(clientService.getClientById(clientId)));
    }

    /**
     * Энпоинт добавления номера телефона клиента.
     * @param clientId идентификатор клиента.
     * @param contactRequest номер телефона.
     * @return ответ с обновленным клиентом.
     */
    @PutMapping("/{client_id}/phones")
    @Operation(summary = "Add a new phone to a client by his id")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#clientId)")
    public ResponseEntity<ClientDto> addPhoneClient(
            @PathVariable("client_id") Long clientId,
            @RequestBody ContactRequest contactRequest){
            Client client = clientService.addPhone(clientId,
                    contactRequest.contact());
            return ResponseEntity.ok(clientMapper.toDto(client));
    }

    /**
     * Энпоинт добавления email.
     * @param clientId идентификатор клиента.
     * @param contactRequest адрес email.
     * @return ответ с обновленным клиентом.
     */
    @PutMapping("/{client_id}/emails")
    @Operation(summary = "Add a new email to a client by his id")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#clientId)")
    public ResponseEntity<ClientDto> addEmailClient(
            @PathVariable("client_id") Long clientId,
            @RequestBody ContactRequest contactRequest){
            Client client = clientService.addEmail(clientId,
                    contactRequest.contact());
            return ResponseEntity.ok(clientMapper.toDto(client));
    }

    /**
     * Энпоинт удаления номера телефона.
     * @param clientId идентификатор клиента.
     * @param contactRequest номер телефона.
     * @return ответ с обновленным клиентом.
     */
    @DeleteMapping("/{client_id}/phones")
    @Operation(summary = "Remove phone to client by his id")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#clientId)")
    public ResponseEntity<ClientDto> removePhoneClient(
            @PathVariable("client_id") Long clientId,
            @RequestBody ContactRequest contactRequest){
            Client client = clientService.removePhone(clientId,
                    contactRequest.contact());
            return ResponseEntity.ok(clientMapper.toDto(client));
    }

    /**
     * Энпоинт удаления email
     * @param clientId идентификатор клиента.
     * @param contactRequest адрес emil.
     * @return ответ с обновленным клиентом.
     */
    @DeleteMapping("/{client_id}/emails")
    @Operation(summary = "Remove email to client by his id")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#clientId)")
    public ResponseEntity<ClientDto> removeEmailClient(
            @PathVariable("client_id") Long clientId,
            @RequestBody ContactRequest contactRequest){
        Client client = clientService.removeEmail(clientId, contactRequest.contact());
        return ResponseEntity.ok(clientMapper.toDto(client));
    }
}
