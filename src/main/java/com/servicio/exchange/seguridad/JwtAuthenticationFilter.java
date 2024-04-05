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
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
			String username = jwtTokenProvider.obtenerUserName(token);
			log.info("[[ doFilterInternal [ username: "+username);
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
			log.info("[[ doFilterInternal-0 [ authenticationToken.toString(): "+authenticationToken.toString());
//			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			log.info("[[ doFilterInternal-1 [ authenticationToken.toString(): "+authenticationToken.toString());
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		filterChain.doFilter(request, response);
	}
	
	private String obtenerToken(HttpServletRequest request) {
		String bearer = request.getHeader("Authorization");
		
		if(StringUtils.hasText(bearer) && bearer.startsWith("Bearer")) {
			String token = bearer.substring(7, bearer.length());
			return token;
		}
		return null;
	}

}
