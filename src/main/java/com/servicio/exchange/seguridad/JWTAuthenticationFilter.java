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

public class JWTAuthenticationFilter extends OncePerRequestFilter{
	
	
	private static final Logger log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

	
	@Autowired
	private JWTTokenProvider jwtTokenProvider;
	
	@Autowired
	private CustomerUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info(" [[ doFilterInternal");
		
		String token = obtenerJWTDeLaSolicitud(request);
		
		if(StringUtils.hasText(token) && jwtTokenProvider.validarToken(token)) {
			String username = jwtTokenProvider.obtenerUsernameDelJwt(token);
			
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		log.info(" ]] doFilterInternal");
		filterChain.doFilter(request, response);
		
	}
	
	private String obtenerJWTDeLaSolicitud(HttpServletRequest request) {
		log.info(" [[ obtenerJWTDeLaSolicitud");
		String bearerToken = request.getHeader("Authorization");
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			log.info(" ]] obtenerJWTDeLaSolicitud");
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
	
}
