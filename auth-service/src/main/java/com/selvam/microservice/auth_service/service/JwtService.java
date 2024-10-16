package com.selvam.microservice.auth_service.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	public static final String SECRET = "0Pfc/hgkl38NvWSoSwD4eWEt5Eem0+op1ANGv7yl+PJzhRUg8cmmnXl8dox9G07r";
	public static final Long EXPIRY_TIME = 60 * 60 * 1000L;

	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date(System.currentTimeMillis()));
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUserName(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<String, Object>();
		return createToken(claims, username);
	}

	private String createToken(Map<String, Object> claims, String username) {
		return Jwts.builder()
				.claims(claims)
				.subject(username)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + EXPIRY_TIME))
				.signWith(getSignKey())
				.compact();
	}

	private SecretKey getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
