package br.com.dbank.adapters.outbound.database.entity;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "client")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity implements UserDetails {

	private static final long serialVersionUID = 8734609162849707411L;

	@Id
	@Column(name = "client_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.UUID)
	private String clientID;

	@Column(name = "client_document", nullable = false, unique = true)
	private String document;
	
	@Column(name = "client_name", nullable = false)
	private String name;

	@Column(name = "client_email", nullable = false, unique = true)
	private String email;

	@Column(name = "client_password", nullable = false)
	private String password;
	
	@Column(name = "created_at", nullable = false)
	private OffsetDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	private OffsetDateTime updatedAt;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "clientId")
	private List<AccountEntity> accounts;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	@Override
	public @Nullable String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.document;
	}

	@Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}
