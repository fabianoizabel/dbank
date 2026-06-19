package br.com.dbank.application.commands;

public record CreateClientCommand(
		String document,
		String name,
		String email,
		String password) {
}
