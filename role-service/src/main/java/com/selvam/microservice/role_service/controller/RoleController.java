package com.selvam.microservice.role_service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selvam.microservice.role_service.service.RoleService;

@RestController
@RequestMapping("/users")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@GetMapping
	public ResponseEntity<Map<String, List<String>>> getAllUsersRole() {
		Map<String, List<String>> usersRoleMap = this.roleService.getAllUserRoles();
		return ResponseEntity.ok(usersRoleMap);
	}
}
