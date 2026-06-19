package br.com.dbank.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.dbank.adapters.outbound.database.repositories.ClientJpaRepository;
import br.com.dbank.application.exceptions.ClientDocumentNotFoundException;

@Configuration
public class ApplicationConfig {

	  private final ClientJpaRepository repository;

	    public ApplicationConfig(ClientJpaRepository repository) {
	        this.repository = repository;
	    }

	    @Bean
	    public UserDetailsService userDetailsService() {
	        return username -> {
				try {
					return repository.findByDocument(username)
							        .orElseThrow(() -> new ClientDocumentNotFoundException("Cliente não encontrado, documento => " + username));
				} catch (ClientDocumentNotFoundException e) {
					throw new UsernameNotFoundException(e.getMessage());
				}
			};
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    public AuthenticationProvider authenticationProvider() throws ClientDocumentNotFoundException {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService());
	        authProvider.setPasswordEncoder(passwordEncoder());
	        return authProvider;
	    }

	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }	
}
