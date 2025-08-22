package com.example.uberprojectauthservice.dtos;

import java.util.Date;

import com.example.uberprojectauthservice.models.Passenger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDto {
	
	private String id;
	
	private String name;
	
	private String email;
	
	private String password; //encrypted password
	
	private String phoneNumber;
	
	private Date createdAt;
	
	public static PassengerDto from(Passenger passenger) {
		 return PassengerDto.builder()
				 .id(passenger.getId().toString())
				 .createdAt(passenger.getCreatedAt())
				 .email(passenger.getEmail())
				 .password(passenger.getPassword())
				 .phoneNumber(passenger.getPhoneNumber())
				 .name(passenger.getName())
				 .build();
	}
}
