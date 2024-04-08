package com.servicio.exchange.seguridad;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {
	
	
	@Value("${app.jwt-secretKey}")
	private String jwtSecret;
	
	@Value("${app.jwt-expiration-millisenconds}")
	private Long jwtExpirationMilliseconds;
	
	public String generarToken(Authentication auth) {
		log.info("Generar Token");
		String username = auth.getName();
		
		Date fecha = new Date();
		Date fechaExpiracion = new Date(fecha.getTime()+jwtExpirationMilliseconds);
		
		String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(fechaExpiracion).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
		log.info("Generar Token: "+token);
		return token;				
	}
	
	public String obtenerUsernameFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		
		return claims.getSubject();
	}
	
	public boolean validarToken(String token) {
		
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
		
	}


}
