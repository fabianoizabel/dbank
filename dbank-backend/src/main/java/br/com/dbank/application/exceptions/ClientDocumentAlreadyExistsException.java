package br.com.dbank.application.exceptions;

public class ClientDocumentAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1601423973403910312L;

	public ClientDocumentAlreadyExistsException(String message) {
		super(message);
	}
}
