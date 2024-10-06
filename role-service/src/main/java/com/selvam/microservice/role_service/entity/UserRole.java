package com.selvam.microservice.role_service.entity;

public class UserRole {
	private String username;
	private String authority;
	
	public UserRole() {
		super();
	}

	public UserRole(String username, String authority) {
		super();
		this.username = username;
		this.authority = authority;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
}
