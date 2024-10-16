package com.selvam.microservice.auth_service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.selvam.microservice.auth_service.model.User;

@Component
public class AuthenticationDaoImpl implements AuthenticationDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public User getUserByUserName(String username) throws UsernameNotFoundException {
		String sql = "select u.username,password,enabled,authority from users u inner join authorities a on a.username=u.username where u.username='" + username + "' limit 1";
		try {
			return this.jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class));
		} catch(EmptyResultDataAccessException e) {
			throw new UsernameNotFoundException("User " + username + " not found");
		}
	}

}
