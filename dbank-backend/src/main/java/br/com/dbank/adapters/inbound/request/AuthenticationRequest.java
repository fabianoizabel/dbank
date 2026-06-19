package br.com.dbank.adapters.inbound.request;

public class AuthenticationRequest {

	private String document;
	private String password;

	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
