package ru.medvedev.bankservice.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.medvedev.bankservice.service.impl.TransferServiceImpl;
import ru.medvedev.bankservice.web.model.TransferRequest;
import ru.medvedev.bankservice.web.model.TransferResponse;

/**
 * Контроллер api банковских операций.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transfer")
@Tag(name = "Transfer Controller", description = "Bank API")
public class TransferController {
    /**
     * Сервис транзакций.
     */
    private final TransferServiceImpl transferService;

    /**
     * Эндпоинт проведения транзакции.
     * @param request запрос с данными для транзакции.
     * @return ответ с подтверждением.
     */
    @PostMapping("/")
    @Operation(summary = "Transfer money between clients")
    public ResponseEntity<TransferResponse> transfer(@RequestBody TransferRequest request){
        transferService.transfer(request.creditClientId(),
                request.debitClientId(), request.sum());
        return ResponseEntity.ok(new TransferResponse("Transfer completed!"));
    }

}
