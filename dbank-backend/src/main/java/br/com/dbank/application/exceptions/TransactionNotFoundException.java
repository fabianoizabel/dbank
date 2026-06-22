package br.com.dbank.application.exceptions;

public class TransactionNotFoundException extends Exception {

	private static final long serialVersionUID = 1601423973403910312L;

	public TransactionNotFoundException(String message) {
		super(message);
	}
}
