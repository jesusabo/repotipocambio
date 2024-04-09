package com.servicio.exchange.seguridad;

import java.text.SimpleDateFormat;
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
		log.info("[[ generarToken");
		String username = auth.getName();
		log.info("[[ generarToken con username :"+username);
		Date fecha = new Date();
		String fechaExpiracion1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(fecha.getTime()+jwtExpirationMilliseconds);
		Date fechaExpiracion = new Date(fecha.getTime()+ jwtExpirationMilliseconds);
		log.info("[[ generarToken con fecha dee expiracion: "+fechaExpiracion1);
		String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(fechaExpiracion).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
		log.info("[[ generarToken con valor: "+token);
		return token;				
	}
	
	public String obtenerUsernameFromToken(String token) {
		log.info("[[ obtenerUsernameFromToken [ token: "+token);
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		log.info("[[ obtenerUsernameFromToken [ usuario obtenido : "+claims.getSubject());
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
