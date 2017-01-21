package com.simonini.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.simonini.entities.PointOfInterest;
import com.simonini.services.PointOfInterestService;

@RestController
@RequestMapping("/points")
public class PointOfInterestController {
	
	@Autowired
	private PointOfInterestService service;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<PointOfInterest> savePointOfInteresf(@Validated PointOfInterest point, Errors errors) {
		PointOfInterest saved = service.save(point, errors);

		ResponseEntity<PointOfInterest> responseEntity = new ResponseEntity<PointOfInterest>(saved, HttpStatus.CREATED);
		
		return responseEntity;
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	ResponseEntity<String> handleArgumentErrors(Exception e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<PointOfInterest> getAllPoints() {	
		return service.findAll();
	}
	
}
