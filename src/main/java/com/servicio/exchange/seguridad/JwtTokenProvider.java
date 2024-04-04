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
public class JwtTokenProvider {
	
	
	private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);


	@Value("${app.jwt-secret}")
	private String jwtSecret;
	
	@Value("${app.jwt-expiration-millisenconds}")
	private int jwtExpirationInMs;

	
	public String generarToken(Authentication authentication) {
		
		log.info(" [[ generarToken");
		String username = authentication.getName();
		
		Date fechaActual = new Date();
		
		Date fechaExpiracion = new Date(fechaActual.getTime() + jwtExpirationInMs);
		
		String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(fechaExpiracion).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

		
		log.info(" ]] generarToken");
		return token;		
	}
	
	
	public String obtenerUsernameDelJwt(String token) {
		log.info(" [[ obtenerUsernameDelJwt");
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		log.info(" ]] obtenerUsernameDelJwt");
		return claims.getSubject();
	}
	
	public boolean validarToken(String token) {
		log.info(" [[ validarToken [ token: "+token);
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
		
			log.info(" ]] validarToken Parser");
			return true;
		} catch (SignatureException ex) {
			log.info("Firma JWT no valida");
			return false;
		} catch (MalformedJwtException ex) {
			log.info("Token JWT no valida");
			return false;
		} catch (ExpiredJwtException ex) {
			log.info("Token JWT caducado");
			return false;
		} catch (UnsupportedJwtException ex) {
			log.info("Token JWT no compatible->"+ex.getMessage());
			log.info("Token JWT no compatible->"+ex.getStackTrace().toString());
			log.info("Token JWT no compatible->"+ex.getLocalizedMessage());
			return false;
		} catch (IllegalArgumentException ex) {
			log.info("La cadena claims JWT esta vacia");
			return false;
		} catch (Exception e) {
			log.info(" ]] validarToken");
			return false;
		}
		
	}
	
	
	
	
}
