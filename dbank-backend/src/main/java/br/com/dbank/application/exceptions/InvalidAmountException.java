package br.com.dbank.application.exceptions;

public class InvalidAmountException extends Exception {

	private static final long serialVersionUID = 1601423973403910312L;

	public InvalidAmountException(String message) {
		super(message);
	}
}
