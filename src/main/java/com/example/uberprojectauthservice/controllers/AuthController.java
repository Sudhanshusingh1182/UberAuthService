package com.example.uberprojectauthservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.uberprojectauthservice.dtos.PassengerDto;
import com.example.uberprojectauthservice.dtos.PassengerSignupRequestDto;
import com.example.uberprojectauthservice.services.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	private AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService= authService;
	}
	
	@PostMapping("/signup/passenger")
	public ResponseEntity<PassengerDto> signUp(@Validated @RequestBody PassengerSignupRequestDto passengerSignupRequestDto){
		  	PassengerDto response = authService.signupPassenger(passengerSignupRequestDto);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/signin/passenger")
	public ResponseEntity<?> signIn(){
		return new ResponseEntity<>(10,HttpStatus.CREATED);
	}
}
