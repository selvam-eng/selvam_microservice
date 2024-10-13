package com.selvam.microservice.user_service.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.selvam.microservice.user_service.entity.User;
import com.selvam.microservice.user_service.model.UserRole;
import com.selvam.microservice.user_service.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public List<UserRole> getAllUsers() throws RestClientException, URISyntaxException {
		List<User> users = this.userRepository.findAll();
		final Map<String, List<String>> roles = getAllUsersRole();
		List<UserRole> userRoleList = users.stream().map(user -> {
			UserRole userRole = new UserRole();
			userRole.setUsername(user.getUsername());
			userRole.setRoles(roles.get(user.getUsername()));
			userRole.setEnabled(user.isEnabled());
			return userRole;
		}).collect(Collectors.toList());
		return userRoleList;
	}
	
	private Map<String, List<String>> getAllUsersRole() throws RestClientException, URISyntaxException {
		String url = "http://ROLE-SERVICE/users";
		ResponseEntity<Object> entity = restTemplate.getForEntity(new URI(url), Object.class);
		return (Map<String, List<String>>) entity.getBody();
	}

}
