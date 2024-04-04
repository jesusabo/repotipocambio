package com.servicio.exchange.seguridad;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	
	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private CustomUserServiceDetails customUserServiceDetails;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("[[ doFilterInternal");
		String token = obtenerJWTDeLaSolicitud(request);
		log.info("[[ doFilterInternal [ token: "+token);
		if(StringUtils.hasText(token) && jwtTokenProvider.validarToken(token)) {
			log.info("[[ doFilterInternal [ Paso 1: "+token);
			String username = jwtTokenProvider.obtenerUsernameDelJwt(token);
			
			UserDetails userDetails = customUserServiceDetails.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		log.info("]] doFilterInternal");
		filterChain.doFilter(request, response);
	}
	
	private String obtenerJWTDeLaSolicitud(HttpServletRequest request) {
		
		log.info("[[ obtenerJWTDeLaSolicitud");
		String bearerToken = request.getHeader("Authorization");
		
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			String bToken = bearerToken.substring(7,bearerToken.length());
			log.info("]] obtenerJWTDeLaSolicitud ] token: "+bToken);
			return bToken;
		}
		log.info("]] Error obtenerJWTDeLaSolicitud");
		return null;
	}

}
