package com.selvam.microservice.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selvam.microservice.auth_service.model.AuthenticationModel;
import com.selvam.microservice.auth_service.service.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody AuthenticationModel credential) {
		Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credential.getUsername(), credential.getPassword()));
		if(authentication.isAuthenticated()) {
			String token = this.jwtService.generateToken(credential.getUsername());
			return ResponseEntity.ok(token);
		}
		return ResponseEntity.ok("Username:- " + credential.getUsername() + " not found");
	}
	
	@GetMapping("/welcome")
	public ResponseEntity<String> welcome() {
		return ResponseEntity.ok("Token Accepted");
	}
}