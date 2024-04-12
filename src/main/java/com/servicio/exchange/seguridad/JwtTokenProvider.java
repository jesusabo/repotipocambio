package com.servicio.exchange.seguridad;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtTokenProvider {
	
	@Value("${app.jwt.secretKey}")
	private String secretKey;
	
	@Value("${app.jwt.expiration-time-milliseconds}")
	private Long expirationMilliseconds;
	
	
	public String generarToken(Authentication authentication) {
		
		String username = authentication.getName();
		
		Date fecha = new Date();
		Date fechaExpiracion = new Date(fecha.getTime() + expirationMilliseconds);
		
		String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(fechaExpiracion).signWith(SignatureAlgorithm.HS512, secretKey).compact();
		
		return token;
	}
	
	public String obtenerUsernameDeToken(String token) {
		
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		String username = claims.getSubject();
		return username;
		
	}
	
	public Boolean validarToken(String token) {
		
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
	

}
