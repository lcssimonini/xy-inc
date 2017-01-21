package com.simonini.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.simonini.entities.PointOfInterest;

public interface PointOfInterestRepository extends MongoRepository<PointOfInterest, String> {

}
