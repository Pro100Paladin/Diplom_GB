package ru.medvedev.bankservice.web.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Запрос для изменения контакта (телефон или email)
 * @param contact
 */
@Schema(description = "Contact request")
public record ContactRequest (
        @Schema(description = "contact", example = "phone or email") String contact){
}
