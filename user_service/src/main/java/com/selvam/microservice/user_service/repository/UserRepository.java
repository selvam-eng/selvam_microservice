package com.selvam.microservice.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.selvam.microservice.user_service.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
