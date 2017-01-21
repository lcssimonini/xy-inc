package com.simonini.services;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
		
		if (point == null) {
			throw new IllegalArgumentException("Não foi passado um ponto para ser salvo");
		}

		if (errors.hasErrors()) {
			for (ObjectError error : errors.getAllErrors()) {
				point.addErrorMessage(error.getDefaultMessage());
			}
			return point;
		}

		return repository.save(point);
	}

	public List<PointOfInterest> findAll() {
		return repository.findAll();
	}

	public List<PointOfInterest> findNearPoints(Integer xReference, Integer yReference, Double distance) {
		DistancePredicate predicate = new DistancePredicate(xReference, yReference, distance);

		List<PointOfInterest> allPoints = repository.findAll();

		List<PointOfInterest> filteredPoints = allPoints.stream().filter(predicate).collect(Collectors.toList());

		return filteredPoints;
	}

	private class DistancePredicate implements Predicate<PointOfInterest> {

		Double distance;
		Integer xPoint;
		Integer yPoint;

		public DistancePredicate(Integer xPoint, Integer yPoint, Double distance) {
			this.distance = distance;
			this.xPoint = xPoint;
			this.yPoint = yPoint;
		}

		@Override
		public boolean test(PointOfInterest point) {
			Double calculatedDistance = point.getDistance(xPoint, yPoint);

			return (calculatedDistance.compareTo(this.distance) < 0);
		}
	}
}
