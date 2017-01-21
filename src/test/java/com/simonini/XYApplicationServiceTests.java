package com.simonini;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.Errors;

import com.simonini.entities.PointOfInterest;
import com.simonini.services.PointOfInterestService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XYApplicationServiceTests {
	
	@Autowired
	private PointOfInterestService service;
	
	Errors errors;
	
	@Before
	public void setUp() {
		errors = Mockito.mock(Errors.class);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void add_nullPOI_ShouldThrowException() {
		service.save(null, errors);
	}
	
	@Test
	public void add_POIWithNoErrors() {
		PointOfInterest poi = new PointOfInterest();
		poi.setName("poi teste service");
		poi.setxCoordinate(1);
		poi.setyCoordinate(1);
		PointOfInterest saved = service.save(poi, errors);
		assertNotNull(saved.getId());
	}
}











