package com.servicio.exchange.seguridad;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = obtenerToken(request);
				
		if(StringUtils.hasText(token) && jwtTokenProvider.validarToken(token)) {
			
			String username = jwtTokenProvider.obtenerUsernameJWT(token);
			
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
			
			UsernamePasswordAuthenticationToken AuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
			
			SecurityContextHolder.getContext().setAuthentication(AuthenticationToken);
			
			
		}
		filterChain.doFilter(request, response);
		
	}
	
	private String obtenerToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

}
