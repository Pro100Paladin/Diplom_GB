package ru.medvedev.bankservice.web.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Ответ о проведении транзакции.
 * @param message сообщение.
 */

@Schema(description = "Transfer response")
public record TransferResponse(
        @Schema(description = "message", example = "Transfer completed!") String message) {
}
