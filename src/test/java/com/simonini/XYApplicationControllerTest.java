package com.simonini;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.io.IOException;

import javax.annotation.Resource;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.simonini.entities.PointOfInterest;
import com.simonini.repository.PointOfInterestRepository;
import com.simonini.util.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XYApplicationControllerTest {
	
	@Autowired
	private PointOfInterestRepository repository;

	@Resource
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		
		PointOfInterest poi1 = new PointOfInterest();
		poi1.setName("poi teste 1");
		poi1.setxCoordinate(0);
		poi1.setyCoordinate(1);
		
		PointOfInterest poi2 = new PointOfInterest();
		poi2.setName("poi teste 2");
		poi2.setxCoordinate(1);
		poi2.setyCoordinate(0);
		
		repository.save(poi1);
		repository.save(poi2);		
	}
	
	@After
	public void tearDown() {
		repository.deleteAll();
	}

	@Test
	public void add_POI_ShoudReturnPOIWithID() throws IOException, Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/points?name=ponto sem erros&xCoordinate=1&yCoordinate=1")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", Matchers.notNullValue()))
				.andExpect(jsonPath("$.errorsList", Matchers.hasSize(0)));
	}

	@Test
	public void add_POIErrorNoName_ShoudReturnPOIWithErrorMessages() throws IOException, Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/points?xCoordinate=1&yCoordinate=1")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

				.andExpect(MockMvcResultMatchers.status().isConflict())
				.andExpect(MockMvcResultMatchers.content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.errorsList", Matchers.hasSize(1)))
				.andExpect(jsonPath("$.errorsList[0]", Matchers.is("Nome não pode ser vazio")));
	}

	@Test
	public void add_POIErrorNoXCoordinate_ShoudReturnPOIWithErrorMessages() throws IOException, Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/points?name=ponto&yCoordinate=1")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

				.andExpect(MockMvcResultMatchers.status().isConflict())
				.andExpect(MockMvcResultMatchers.content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.errorsList", Matchers.hasSize(1)))
				.andExpect(jsonPath("$.errorsList[0]", Matchers.is("coordenada x não pode ser vazia")));
	}

	@Test
	public void add_POIErrorNoYCoordinate_ShoudReturnPOIWithErrorMessages() throws IOException, Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/points?name=ponto&xCoordinate=1")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

				.andExpect(MockMvcResultMatchers.status().isConflict())
				.andExpect(MockMvcResultMatchers.content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.errorsList", Matchers.hasSize(1)))
				.andExpect(jsonPath("$.errorsList[0]", Matchers.is("coordenada y não pode ser vazia")));
	}

	@Test
	public void add_POIErrorNoFilds_ShoudReturnPOIWithErrorMessages() throws IOException, Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/points").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

				.andExpect(MockMvcResultMatchers.status().isConflict())
				.andExpect(MockMvcResultMatchers.content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.errorsList", Matchers.hasSize(3)))
				.andExpect(jsonPath("$.errorsList", Matchers.containsInAnyOrder("Nome não pode ser vazio",
						"coordenada y não pode ser vazia", "coordenada x não pode ser vazia")));
	}

	@Test
	public void listPOIS_ShoudReturnPOIList() throws IOException, Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/points").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", Matchers.hasSize(2)));
	}
}
