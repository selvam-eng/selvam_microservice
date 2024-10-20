package com.selvam.microservice.api_gateway.model;

public class UserInfo {
	private String username;
	private String role;
	
	public UserInfo() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
