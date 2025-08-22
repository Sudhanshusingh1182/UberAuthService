package com.example.uberprojectauthservice.models;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass  
public abstract class BaseModel {
	   @Id      
	   @GeneratedValue(strategy = GenerationType.IDENTITY)  
		protected Long id;
	   
	   @Column(nullable=false)
	   @Temporal(TemporalType.TIMESTAMP) 
	   @CreatedDate  
	   protected Date createdAt;
	   
	   @Column(nullable = false)
	   @Temporal(TemporalType.TIMESTAMP)
	   @LastModifiedDate 
	   protected Date updatedAt;
}

