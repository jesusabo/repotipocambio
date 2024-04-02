package com.servicio.exchange.seguridad;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JWTTokenProvider {
	
	
	private static final Logger log = LoggerFactory.getLogger(JWTTokenProvider.class);

	
	@Value("${app.jwt-secret}")
	private String jwtSecret;
	
	@Value("${app.jwt-expiration-milliseconds}")
	private int jwtExpirationInMs;
	
	public String generarToken(Authentication authentication) {
		
		log.info("[[ generarToken [ username: "+authentication.getName());
		
		String username = authentication.getName();
		Date fechaActual = new Date();
		Date fechaExpiracion = new Date(fechaActual.getTime()+jwtExpirationInMs);
		
		String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(fechaExpiracion).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
		
		log.info("]] generarToken");
		return token;
		
	}
	
	public String obtenerUsernameDelJwt(String token) {
		
		log.info("[[ obtenerUsernameDelJwt");
		
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		
		log.info("]] obtenerUsernameDelJwt");
		return claims.getSubject();
	}
	
	public boolean validarToken(String token) {
		
		log.info("[[ validarToken");
		
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			log.info("]] validarToken");
			return true;
		} catch (SignatureException ex) {
			//throw new RuntimeException(HttpStatus.BAD_REQUEST, "Firma JET no valida");
			ex.getStackTrace();
		} catch (MalformedJwtException ex) {
			ex.getStackTrace();
		} catch (ExpiredJwtException ex) {
			ex.getStackTrace();
		} catch (UnsupportedJwtException ex) {
			ex.getStackTrace();
		} catch (IllegalArgumentException ex) {
			ex.getStackTrace();
		} catch (Exception e) {
			e.getStackTrace();
		}
		log.info("]] validarToken");
		return false;
	}
	

}
