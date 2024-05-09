package com.servicio.exchange.seguridad;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenProvider {
	
	@Value("${app.jwt-securekey}")
	private String secureKey;
	
	@Value("${app.jwt-expiration-time-milliseconds}")
	private Long expirationTimeMilliseconds;
	
	
	
	public String generarToken(Authentication authentication) {
		
		String name = authentication.getName();
		
		Date fecha = new Date();
		Date fechaExpiracion = new Date(fecha.getTime()+expirationTimeMilliseconds);
		
		String token = Jwts.builder().setSubject(name).setIssuedAt(new Date()).setExpiration(fechaExpiracion).signWith(SignatureAlgorithm.HS512, secureKey).compact();
		
		return token;
		
	}
	
	public String obtenerUsernameJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(secureKey).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
	
	public Boolean validarToken(String token) {
		try {
			Jwts.parser().setSigningKey(secureKey).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
	
			e.printStackTrace();
			return false;
		}
		
	}

}
