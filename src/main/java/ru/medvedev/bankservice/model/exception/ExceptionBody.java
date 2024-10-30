package ru.medvedev.bankservice.model.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Тело ответа при исключениях.
 */
@Getter
@Setter
@Schema(description = "Exception body")
public class ExceptionBody {
    public ExceptionBody(String message) {
        this.message = message;
        this.localDateTime = LocalDateTime.now();
    }

    /**
     * Сообщение.
     */
    @Schema(description = "message", example = "exception message")
    private String message;
    /**
     * Время исключения.
     */
    @Schema(description = "localDateTime", example = "exception message")
    private LocalDateTime localDateTime;
    /**
     * Коллекция для полей объекта валидации.
     */
    @Schema(description = "Errors map",
            example = "{\"field1\":\"Error message 1\",\"field2\":\"Error message 2\"}")
    private Map<String, String> errors;
}
