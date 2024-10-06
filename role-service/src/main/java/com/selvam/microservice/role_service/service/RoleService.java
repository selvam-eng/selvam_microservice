package com.selvam.microservice.role_service.service;

import java.util.List;
import java.util.Map;

public interface RoleService {
	public Map<String, List<String>> getAllUserRoles();
}
