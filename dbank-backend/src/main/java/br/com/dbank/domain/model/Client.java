package br.com.dbank.domain.model;

import java.time.OffsetDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Client {

	String clientID;
	String document;
	String name;
	String email;
	String password;
	OffsetDateTime createdAt;
	OffsetDateTime updatedAt;

}
