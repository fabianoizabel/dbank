package br.com.dbank.adapters.inbound.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dbank.adapters.inbound.response.ErrorResponse;
import br.com.dbank.adapters.inbound.response.TransactionResponse;
import br.com.dbank.usecases.ListAccountTransactionsUseCase;

@RestController
@RequestMapping("/dbank/api/v1/accounts")
public class AccountController {

	private final ListAccountTransactionsUseCase useCase;

    public AccountController(ListAccountTransactionsUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("{accountId}/transactions")
    public ResponseEntity<?> listAccountTransactions(@PathVariable(name = "accountId") String accountId) {
        try {
            List<TransactionResponse> response = useCase.execute(accountId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        } catch (Exception e) {
        	ErrorResponse response = new ErrorResponse(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
    }
}
