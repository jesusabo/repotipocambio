package com.servicio.exchange.seguridad;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtTokenGenerator jwtTokenGenerator;
	
	@Autowired
	private CustomUserDetailsService customeUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = obtenerToken(request);
		
		if(StringUtils.hasText(token) && jwtTokenGenerator.validarToken(token)) {
			String username = jwtTokenGenerator.obtenerUsernameDeJWT(token);
			UserDetails userDetails = customeUserDetailsService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null,userDetails.getAuthorities());
			
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
						
			
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	private String obtenerToken(HttpServletRequest request) {
		String bearer = request.getHeader("Authorization");
		
		if(StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
			return bearer.substring(7,bearer.length());
		}
		return null;
	}

}
