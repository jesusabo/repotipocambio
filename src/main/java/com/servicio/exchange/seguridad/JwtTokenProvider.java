package com.servicio.exchange.seguridad;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtTokenProvider {
	
	@Value("${app.jwt-secret}")
	private String secretKey;

	@Value("${app.jwt-expiration-milliseconds}")
	private Long expirationMilliseconds;
	
	
	public String generarToken(Authentication authentication) {
		
		String username = authentication.getName();
		log.info("generarToken :"+username);
		Date fecha = new Date();
		log.info("Fecha time: "+fecha.getTime());
		Date fechaExpiracion = new Date(fecha.getTime() + expirationMilliseconds);
		
		String token = Jwts.builder().setSubject(username).setIssuedAt(fecha).setExpiration(fechaExpiracion).signWith(SignatureAlgorithm.HS512,secretKey).compact();
		return token;
	}
	
	public String obtenerUserName(String token) {
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
	
	public boolean validarToken(String token) {		
		
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (UnsupportedJwtException ex) {
			return false;
		} catch (MalformedJwtException ex) {
			return false;
		} catch (SignatureException ex) {
			return false;
		} catch (ExpiredJwtException ex) {
			return false;
		} catch (IllegalArgumentException ex) {
			return false;			
		} catch (Exception e) {
			return false;
		}
		
	}
}
