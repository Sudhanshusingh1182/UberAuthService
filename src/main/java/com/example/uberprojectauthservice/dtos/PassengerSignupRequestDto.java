package com.example.uberprojectauthservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PassengerSignupRequestDto {
	 
	private String email;
	
	private String password;
	
	private String phoneNumber;
	
	private String name;
}
