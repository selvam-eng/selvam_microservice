package com.selvam.microservice.role_service.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.selvam.microservice.role_service.dao.UserRoleDao;
import com.selvam.microservice.role_service.entity.UserRole;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private UserRoleDao userRoleDao;

	@Override
	public Map<String, List<String>> getAllUserRoles() {
		List<UserRole> userRoleList = this.userRoleDao.getAllUsersRole();
		Map<String, List<String>> userRoleMap = userRoleList.stream()
				.collect(Collectors.groupingBy(userRole -> userRole.getUsername(), 
						Collectors.mapping(userRole -> userRole.getAuthority(), Collectors.toList())));
		return userRoleMap;
	}

}
