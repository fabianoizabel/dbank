package br.com.dbank.application.exceptions;

public class ClientDocumentNotFoundException extends Exception {

	private static final long serialVersionUID = 1601423973403910312L;

	public ClientDocumentNotFoundException(String message) {
		super(message);
	}
}
