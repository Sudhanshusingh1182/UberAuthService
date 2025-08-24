package com.example.uberprojectauthservice.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.uberprojectauthservice.services.JwtService;
import com.example.uberprojectauthservice.services.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	private final RequestMatcher uriMatcher= new AntPathRequestMatcher("/api/v1/auth/validate", HttpMethod.GET.name());
	
	private final JwtService jwtService;
	
	public JwtAuthFilter(JwtService jwtService) {
		this.jwtService=jwtService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = null;
		if(request.getCookies()!= null) {
			for(Cookie cookie: request.getCookies()) {
				if(cookie.getName().equals("JwtToken")) {
					token = cookie.getValue();
				}
			}
		}
		
		if(token == null) {
				//user has not provided any jwt token hence request should not go forward
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);	
			return;
		}
		
		log.info("Incoming token: {}",token);
		
		//If token is actually present
		String email = jwtService.extractEmail(token);
		
		log.info("Incoming email: {}",email);
		
		//check if an user with this email exists or not and also validate token
		if(email != null) {
			 UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(email);
			 if(jwtService.validateToken(token, userDetails.getUsername())) {
				 	 UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(userDetails, null);
				 	 usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				 	 SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			 }
		}
		log.info("Forwarding request");
		//call the next filter/method (it is similar to middleware in node.js)
		filterChain.doFilter(request, response);
		
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {  
		return uriMatcher.matches(request);
	}

}
