package ru.medvedev.bankservice.web.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * Запрос транзакции.
 * @param creditClientId идентификатор клиента плотильщика.
 * @param debitClientId идентификатор клиента получателя.
 * @param sum сумма перевода.
 */
@Schema(description = "Transfer request")
public record TransferRequest(
        @Schema(description = "creditClientId", example = "1") Long creditClientId,
        @Schema(description = "debitClientId", example = "2") Long debitClientId,
        @Schema(description = "sum", example = "200.00") BigDecimal sum) {
}
