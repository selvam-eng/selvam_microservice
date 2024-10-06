package com.selvam.microservice.user_service.service;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.web.client.RestClientException;

import com.selvam.microservice.user_service.model.UserRole;

public interface UserService {
	
	public List<UserRole> getAllUsers() throws RestClientException, URISyntaxException;
}
