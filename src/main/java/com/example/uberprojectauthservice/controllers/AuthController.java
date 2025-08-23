package com.example.uberprojectauthservice.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.uberprojectauthservice.dtos.AuthRequestDto;
import com.example.uberprojectauthservice.dtos.AuthResponseDto;
import com.example.uberprojectauthservice.dtos.PassengerDto;
import com.example.uberprojectauthservice.dtos.PassengerSignupRequestDto;
import com.example.uberprojectauthservice.services.AuthService;
import com.example.uberprojectauthservice.services.JwtService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {
	
	@Value("${cookie.expiry}")
	private int cookieExpiry;
	
	private final AuthService authService;
	
	private final AuthenticationManager authenticationManager;
	
	private final JwtService jwtService;
	
	public AuthController(AuthService authService,AuthenticationManager authenticationManager, JwtService jwtService) {
		this.authService= authService;
		this.authenticationManager=authenticationManager;
		this.jwtService=jwtService;
	}
	
	@PostMapping("/signup/passenger")
	public ResponseEntity<PassengerDto> signUp(@Validated @RequestBody PassengerSignupRequestDto passengerSignupRequestDto){
		  	PassengerDto response = authService.signupPassenger(passengerSignupRequestDto);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("/signin/passenger")
	public ResponseEntity<?> signIn(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse httpServletResponse){
		log.info("Request for sign in received: email:{}, password:{}", authRequestDto.getEmail(), authRequestDto.getPassword());
		Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword()));
		
		if(authentication.isAuthenticated()) {
			String jwtToken = jwtService.createToken(authRequestDto.getEmail());
			ResponseCookie responseCookie = ResponseCookie.from("JwtToken", jwtToken)
					.httpOnly(true)
					.secure(false)
					.path("/")
					.maxAge(cookieExpiry).build();
			
			httpServletResponse.setHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
			return new ResponseEntity<>(AuthResponseDto.builder().success(true).build(), HttpStatus.OK);
		} else {
			throw new UsernameNotFoundException("User not found");
		}
	}
}
