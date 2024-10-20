package com.selvam.microservice.api_gateway.constant;

import java.util.HashMap;
import java.util.Map;

public class GatewayConstants {

	public static Map<String, Integer> ROLES_MAP = new HashMap<String, Integer>();
	
	static {
		ROLES_MAP.put("ROLE_ADMIN", 1);
		ROLES_MAP.put("ROLE_USER", 2);
	}
}