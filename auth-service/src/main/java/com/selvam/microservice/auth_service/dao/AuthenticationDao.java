package com.selvam.microservice.auth_service.dao;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.selvam.microservice.auth_service.model.User;

public interface AuthenticationDao {
	public User getUserByUserName(String username) throws UsernameNotFoundException;
}
