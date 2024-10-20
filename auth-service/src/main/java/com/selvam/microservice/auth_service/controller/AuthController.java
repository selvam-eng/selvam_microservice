package com.selvam.microservice.auth_service.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selvam.microservice.auth_service.model.AuthenticationModel;
import com.selvam.microservice.auth_service.model.UserInfoResponse;
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
			String role = authentication.getAuthorities().stream().collect(Collectors.toList()).get(0).getAuthority();
			String token = this.jwtService.generateToken(credential.getUsername(), role);
			return ResponseEntity.ok(token);
		}
		return ResponseEntity.ok("Username:- " + credential.getUsername() + " not found");
	}
	
	@GetMapping("/validate/token")
	public ResponseEntity<UserInfoResponse> validateToken(@RequestHeader("Authorization") String authHeader) {
		if(authHeader!=null && authHeader.startsWith("Bearer")) {
			String token = authHeader.substring(7);
			String username = this.jwtService.extractUserName(token);
			String role = this.jwtService.extractRoles(token);
			return ResponseEntity.ok(new UserInfoResponse(username, role));
		} return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
}