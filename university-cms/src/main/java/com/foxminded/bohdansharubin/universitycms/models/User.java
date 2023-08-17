package com.foxminded.bohdansharubin.universitycms.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails{
	private static final String DEFAULT_USERNAME_PATTERN = "^[a-zA-Z][a-zA-Z0-9]+";
	
	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true)
	@NotNull
	@Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]+")
	private String username;
	
	@ToString.Exclude
	@NotBlank
	private String password;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "role")
	private Roles role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		final List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(role);
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public boolean isValid() {
		return id >= 0 &&
			isUsernameValid() &&
			isPasswordValid();
	}
	
	private boolean isUsernameValid() {
		if(username.matches(DEFAULT_USERNAME_PATTERN)) {
			return true;
		} 
		return false;
	}
	
	private boolean isPasswordValid() {
		if(password.trim().isEmpty()) {
			return false;
		} 
		return true;
	}

}
