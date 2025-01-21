package com.task.library.management.RollBasedAuthentication;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.task.library.management.Entity.UserAccount;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
	
	private UserAccount userAccount;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return userAccount.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_"+role.getName()))
				.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return userAccount.getPassword();
	}

	@Override
	public String getUsername() {
		return userAccount.getUserName();
	}

}
