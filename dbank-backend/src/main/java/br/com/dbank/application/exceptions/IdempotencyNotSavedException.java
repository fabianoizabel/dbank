package br.com.dbank.application.exceptions;

public class IdempotencyNotSavedException extends Exception {

	private static final long serialVersionUID = 1601423973403910312L;

	public IdempotencyNotSavedException(String message) {
		super(message);
	}
}
