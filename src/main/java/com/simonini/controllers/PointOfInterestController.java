package com.simonini.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.simonini.entities.PointOfInterest;
import com.simonini.repository.PointOfInterestRepository;

@RestController
@RequestMapping("/points")
public class PointOfInterestController {
	
	@Autowired
	private PointOfInterestRepository repository;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<PointOfInterest> savePointOfInteresf(PointOfInterest point) {
		PointOfInterest savedEntity = repository.save(point);
		ResponseEntity<PointOfInterest> responseEntity = new ResponseEntity<PointOfInterest>(savedEntity, HttpStatus.CREATED);
		return responseEntity;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<PointOfInterest> getAllPoints() {
		List<PointOfInterest> allPoints = repository.findAll();
		
		return allPoints;
	}
	
}
