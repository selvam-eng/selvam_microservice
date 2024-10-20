package com.selvam.microservice.auth_service.model;

public class UserInfoResponse {
	private String username;
	private String role;
	
	public UserInfoResponse() {
		super();
	}

	public UserInfoResponse(String username, String role) {
		super();
		this.username = username;
		this.role = role;
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

	@Override
	public String toString() {
		return "UserInfoResponse [username=" + username + ", role=" + role + "]";
	}
	
}
