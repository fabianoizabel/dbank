package br.com.dbank.adapters.inbound.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dbank.adapters.inbound.request.AuthenticationRequest;
import br.com.dbank.adapters.inbound.response.ErrorResponse;
import br.com.dbank.infrastructure.configuration.JwtService;

@RestController
@RequestMapping("/dbank/api/v1/public/auth")
public class AuthenticationController {

	 private final AuthenticationManager authenticationManager;
	 private final UserDetailsService userDetailsService;
	 private final JwtService jwtService;

	 public AuthenticationController(AuthenticationManager authenticationManager, 
	                                 UserDetailsService userDetailsService, 
	                                 JwtService jwtService) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtService = jwtService;
	 }

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
		try {
		    authenticationManager.authenticate(
		            new UsernamePasswordAuthenticationToken(request.getDocument(), request.getPassword())
		    );
		} catch (Exception e) {
        	ErrorResponse response = new ErrorResponse(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
		}
	    final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getDocument());
	
	    final String jwt = jwtService.generateToken(userDetails);
	
	    return ResponseEntity.ok(Map.of("token", jwt));
	}	
}
