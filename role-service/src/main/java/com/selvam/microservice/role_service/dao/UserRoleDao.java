package com.selvam.microservice.role_service.dao;

import java.util.List;

import com.selvam.microservice.role_service.entity.UserRole;

public interface UserRoleDao {
	public List<UserRole> getAllUsersRole();
}
