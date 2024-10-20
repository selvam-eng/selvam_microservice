package com.selvam.microservice.api_gateway.filter;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import com.selvam.microservice.api_gateway.constant.GatewayConstants;
import com.selvam.microservice.api_gateway.model.UserInfo;

import reactor.core.publisher.Mono;

@Component
public class RoleBasedFilter extends AbstractGatewayFilterFactory<RoleBasedFilter.Config> {
	
	private static final Logger log = LoggerFactory.getLogger(RoleBasedFilter.class);
	private static final String AUTH_SERVICE = "AUTH-SERVICE";
	private static final String AUTH_HEADER_NAME = "Authorization";
	
	@Autowired
	private WebClient webClient;
	
	public RoleBasedFilter() {
        super(Config.class);
    }
	
	@Override
	public GatewayFilter apply(Config config) {

		return ((exchange, chain) -> {
			String authHeader = exchange.getRequest().getHeaders().getFirst(AUTH_HEADER_NAME);
			if (authHeader == null || !authHeader.startsWith("Bearer")) {
				return unAuthorized(exchange);
			}
			return this.webClient.get()
					.uri("lb://" + AUTH_SERVICE + "/auth/validate/token")
					.header(AUTH_HEADER_NAME, authHeader).retrieve()
					.onStatus(status -> status.is4xxClientError(), response -> {
						exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
						return Mono.empty();
					}).bodyToMono(UserInfo.class).flatMap(userInfo -> {
						String path = exchange.getRequest().getPath().value();
						String requiredRole = config.pathRoleMap.get(path);
						if (userInfo == null) {
							return unAuthorized(exchange);
						} else if (GatewayConstants.ROLES_MAP.get(requiredRole) < GatewayConstants.ROLES_MAP.get(userInfo.getRole())) {
							return forbidden(exchange);
						}
						return chain.filter(exchange);
					});
		});
	}
	
	private Mono<Void> unAuthorized(ServerWebExchange exchange) {
		exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		return exchange.getResponse().setComplete();
	}
	
	private Mono<Void> forbidden(ServerWebExchange exchange) {
		exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
		return exchange.getResponse().setComplete();
	}

	public static class Config {
		private Map<String, String> pathRoleMap;

		public Map<String, String> getPathRoleMap() {
			return pathRoleMap;
		}

		public void setPathRoleMap(Map<String, String> pathRoleMap) {
			this.pathRoleMap = pathRoleMap;
		}
	}
}
