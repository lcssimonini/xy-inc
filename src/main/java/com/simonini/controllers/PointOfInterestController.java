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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simonini.entities.PointOfInterest;
import com.simonini.services.PointOfInterestService;

@RestController
@RequestMapping("/points")
public class PointOfInterestController {

	@Autowired
	private PointOfInterestService service;
	
	/**
	 * 
	 * @param point (ponto é instanciado a partir dos campos fornecidos na requisição POST)
	 * @param errors (contém as mensagens de validação caso existam)
	 * @return ponto salvo (id != null) ou ponto com erros de validação (id == null e errorsList com mensagens de erro)
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<PointOfInterest> savePointOfInteresf(@Validated PointOfInterest point, Errors errors) {
		PointOfInterest saved = service.save(point, errors);
		
		HttpStatus status = null;
		
		if (saved.hasErrors()) {
			status = HttpStatus.CONFLICT;
		} else {
			status = HttpStatus.CREATED;
		}
		
		ResponseEntity<PointOfInterest> responseEntity = new ResponseEntity<PointOfInterest>(saved, status);
		return responseEntity;
	}
	
	/**
	 * 
	 * @return todos os pontos salvos na base
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<PointOfInterest> getAllPoints() {
		return service.findAll();
	}
	
	/**
	 * 
	 * @param xReference - x do ponto de referência para cálculo da distância 
	 * @param yReference - y do ponto de referência para cálculo da distância
	 * @param distance - distância dos pontos que se deseja pesquisar
	 * @return - lista de pontos contidos no raio fornecido (distância)
	 */
	@RequestMapping(value = "/findNear", method = RequestMethod.GET)
	public List<PointOfInterest> getNearPoints(@RequestParam("xReference") Integer xReference,
			@RequestParam("yReference") Integer yReference, @RequestParam("distance") Double distance) {

		return service.findNearPoints(xReference, yReference, distance);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	ResponseEntity<String> handleArgumentErrors(Exception e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
	}
}
