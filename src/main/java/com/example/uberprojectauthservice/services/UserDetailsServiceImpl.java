package com.example.uberprojectauthservice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.uberprojectauthservice.models.Passenger;
import com.example.uberprojectauthservice.repositories.PassengerRepository;
import com.example.uberprojectauthservice.utils.AuthPassengerDetails;

/**
 * This class is responsible for loading the user in the form of userDetails object for auth
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private  PassengerRepository passengerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		 Optional<Passenger> passenger= passengerRepository.findPassengerByEmail(email); //email is the unique identifier
		 
		 if(passenger.isPresent()) {
			 return  new AuthPassengerDetails(passenger.get());
		 } else {
			 throw new UsernameNotFoundException("Cannot find the Passenger by the given Email");
		 }
	
	}

}
