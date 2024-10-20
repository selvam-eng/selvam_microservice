package com.selvam.microservice.api_gateway.config;

import java.util.Map;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.selvam.microservice.api_gateway.filter.RoleBasedFilter;

@Configuration
public class RouterConfig {

	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder, RoleBasedFilter roleBasedFilter) {
		RoleBasedFilter.Config config = new RoleBasedFilter.Config();
		config.setPathRoleMap(Map.of("/users", "ROLE_ADMIN", "/users/user", "ROLE_USER"));
		return builder.routes().route("user-service", route -> {
			return route.path("/users/**")
					.filters(filter -> {
				return filter.filter(roleBasedFilter.apply(config));
			}).uri("lb://USER-SERVICE");
		}).route("auth-service", route -> {
			return route.path("/auth/**").uri("lb://AUTH-SERVICE");
		}).build();
	}
}
