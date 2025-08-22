package com.example.uberprojectauthservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.uberprojectauthservice.models.Passenger;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

}
