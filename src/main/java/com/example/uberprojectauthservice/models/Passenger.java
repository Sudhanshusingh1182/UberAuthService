package com.example.uberprojectauthservice.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Passenger extends BaseModel {
      	
	    @Column(nullable = false)
		private String name;
		
		@Column(nullable = false)
		private String phoneNumber;
		
		@Column(nullable = false)
		private String email;
		
		@Column(nullable = false)
		private String password;
		
		@OneToMany(mappedBy = "passenger")
		private List<Booking> bookings;
}

