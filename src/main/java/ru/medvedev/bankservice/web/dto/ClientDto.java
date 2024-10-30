package ru.medvedev.bankservice.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Dto объект клиента.
 */
@Data
@Schema(description = "Client Dto")
public class ClientDto {

    @NotNull(message = "Username must be filled!")
    @Size(min = 4, max = 255)
    @Schema(description = "username", example = "ivan11")
    private String username;
    @NotNull(message = "Password must be filled!")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "Client crypted password",
            example = "123")
    private String password;
    @NotNull(message = "Firstname must be filled!")
    @Schema(description = "Client name", example = "Иванов Иван Иванович")
    private String name;
    @NotNull(message = "Birthday must be filled!")
    @Schema(description = "Client birthday", example = "1990-01-31")
    private LocalDate birthday;
    @ArraySchema(schema = @Schema(description = "Client phone number", example = "+7-911-999-99-99"))
    @Size(min = 1, message = "Client must have at least one phone")
    private List<String> phones;

    @Size(min = 1, message = "Client must have at least one email")
    @ArraySchema(schema = @Schema(description = "Client email", example = "ivan@mail.ru"))
    private List<String> emails;
    @NotNull
    @Positive(message = "Balance must be greater than zero")
    @Schema(description = "Client balance", example = "100.00")
    private BigDecimal balance;
}
