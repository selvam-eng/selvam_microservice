package com.selvam.microservice.user_service.controller;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.selvam.microservice.user_service.model.UserRole;
import com.selvam.microservice.user_service.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<UserRole>> getAllUsers() throws RestClientException, URISyntaxException {
		List<UserRole> userList = this.userService.getAllUsers();
		return ResponseEntity.ok(userList);
	}
}
