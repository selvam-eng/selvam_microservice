package com.selvam.microservice.role_service.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.selvam.microservice.role_service.entity.UserRole;

@Component
public class UserRoleDaoImpl implements UserRoleDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<UserRole> getAllUsersRole() {
		String sql = "select * from authorities";
		return this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<UserRole>(UserRole.class));
	}

}
