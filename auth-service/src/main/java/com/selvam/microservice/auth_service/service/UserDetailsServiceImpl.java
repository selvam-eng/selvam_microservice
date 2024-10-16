package com.selvam.microservice.auth_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.selvam.microservice.auth_service.dao.AuthenticationDao;
import com.selvam.microservice.auth_service.model.MyUserDetails;
import com.selvam.microservice.auth_service.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private AuthenticationDao authenticationDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.authenticationDao.getUserByUserName(username);
		return new MyUserDetails(user);
	}

}
