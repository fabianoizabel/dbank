package br.com.dbank.adapters.inbound.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dbank.adapters.inbound.request.CreateClientRequest;
import br.com.dbank.adapters.inbound.response.ClientResponse;
import br.com.dbank.adapters.inbound.response.ErrorResponse;
import br.com.dbank.application.commands.CreateClientCommand;
import br.com.dbank.application.exceptions.ClientDocumentAlreadyExistsException;
import br.com.dbank.usecases.CreateClientUseCase;

@RestController
@RequestMapping("/dbank/api/v1/clients")
public class ClientController {

	private final CreateClientUseCase useCase;

    public ClientController(CreateClientUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateClientRequest request) {
        CreateClientCommand command =
                new CreateClientCommand(
                        request.getDocument(),
                        request.getName(),
                        request.getEmail(),
                        request.getPassword());

        try {
            ClientResponse response = useCase.execute(command);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        } catch (ClientDocumentAlreadyExistsException clientException) {
        	ErrorResponse response = new ErrorResponse(clientException.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        } catch (Exception e) {
        	ErrorResponse response = new ErrorResponse(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
    }	
}
