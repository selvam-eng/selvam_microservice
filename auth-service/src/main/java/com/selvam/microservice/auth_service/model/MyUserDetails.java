package com.selvam.microservice.auth_service.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private boolean isActive;
	private String role;
	

	public MyUserDetails(User user) {
		super();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.isActive = user.isEnabled();
		this.role = user.getAuthority();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(this.role);
		List<SimpleGrantedAuthority> authorityList = new ArrayList<SimpleGrantedAuthority>();
		authorityList.add(grantedAuthority);
		return authorityList;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}
	
	public boolean isActive() {
		return this.isActive;
	}

}
