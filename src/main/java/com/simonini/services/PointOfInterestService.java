package com.simonini.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.simonini.entities.PointOfInterest;
import com.simonini.repository.PointOfInterestRepository;

@Service
public class PointOfInterestService {
	
	@Autowired
	private PointOfInterestRepository repository;
	
	public PointOfInterest save(PointOfInterest point, Errors errors) {
		if (errors.hasErrors()) {
			StringBuilder errorsString = new StringBuilder();
			for (ObjectError error : errors.getAllErrors()) {
				errorsString.append(error.getDefaultMessage()+"\n");
			}
			
			throw new IllegalArgumentException(errorsString.toString());
		} 
		
		return repository.save(point);
	}
	
	public List<PointOfInterest> findAll() {
		return repository.findAll();
	}
}
