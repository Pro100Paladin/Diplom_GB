package ru.medvedev.bankservice.web.mapper;

import ru.medvedev.bankservice.model.Client;
import ru.medvedev.bankservice.web.dto.ClientDto;

/**
 * Интерфейс преобразования Dto в Entity и наоборот.
 */
public interface ClientMapper {
    /**
     * Преобразование в dto.
     * @param client объект сущности.
     * @return объект dto.
     */
    ClientDto toDto(Client client);

    /**
     * Преобразование в сущность.
     * @param clientDto объект dto.
     * @return объект сущности.
     */
    Client toEntity(ClientDto clientDto);
}
