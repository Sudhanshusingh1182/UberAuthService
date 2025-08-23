package com.example.uberprojectauthservice.utils;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.uberprojectauthservice.models.Passenger;

public class AuthPassengerDetails extends Passenger implements UserDetails {

	private String username; // email/ name/ id

	private String password;

	public AuthPassengerDetails(Passenger passenger) {
		this.username = passenger.getEmail();
		this.password = passenger.getPassword();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	// Below set of methods are not much of a concern

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
}
