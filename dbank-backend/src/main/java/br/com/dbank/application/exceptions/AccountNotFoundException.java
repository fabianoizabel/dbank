package br.com.dbank.application.exceptions;

public class AccountNotFoundException extends Exception {

	private static final long serialVersionUID = 1601423973403910312L;

	public AccountNotFoundException(String message) {
		super(message);
	}
}
