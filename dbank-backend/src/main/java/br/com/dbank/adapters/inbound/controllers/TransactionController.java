package br.com.dbank.adapters.inbound.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dbank.adapters.inbound.request.CreateTransferRequest;
import br.com.dbank.adapters.inbound.response.ErrorResponse;
import br.com.dbank.adapters.inbound.response.TransactionResponse;
import br.com.dbank.application.commands.CreateTransferCommand;
import br.com.dbank.usecases.CreateTransferUseCase;

@RestController
@RequestMapping("/dbank/api/v1/transfer")
public class TransactionController {

	private final CreateTransferUseCase useCase;

    public TransactionController(CreateTransferUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<?> transfer(@RequestBody CreateTransferRequest request) {
        CreateTransferCommand command =
                new CreateTransferCommand(
                        request.getIdempotencyKey(),
                        request.getSourceAccountID(),
                        request.getDestinationAccountID(),
                        request.getAmount());

        try {
            TransactionResponse response = useCase.execute(command);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        } catch (Exception e) {
        	ErrorResponse response = new ErrorResponse(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
    }
}
