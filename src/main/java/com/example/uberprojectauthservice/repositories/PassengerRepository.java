package com.example.uberprojectauthservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.uberprojectauthservice.models.Passenger;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

	Optional<Passenger> findPassengerByEmail(String email);
}
