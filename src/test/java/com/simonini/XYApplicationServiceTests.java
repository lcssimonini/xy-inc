package com.simonini;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.simonini.services.PointOfInterestService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XYApplicationServiceTests {
	
	@Autowired
	private PointOfInterestService service;
	
	@Test
	public void contextLoads() {
	}

}
