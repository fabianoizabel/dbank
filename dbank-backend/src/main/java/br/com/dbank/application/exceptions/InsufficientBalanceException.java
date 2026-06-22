package br.com.dbank.application.exceptions;

public class InsufficientBalanceException extends Exception {

	private static final long serialVersionUID = 1601423973403910312L;

	public InsufficientBalanceException(String message) {
		super(message);
	}
}
