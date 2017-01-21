package com.simonini;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.io.IOException;

import javax.annotation.Resource;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.simonini.util.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XYApplicationControllerTests {

	@Resource
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void add_POI_ShoudReturnPOIWithID() throws IOException, Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/points?name=ponto sem erros&xCoordinate=1&yCoordinate=1")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", Matchers.notNullValue()));
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
	public void listPOIS_ShoudReturnPOIList() throws IOException, Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/points?name=ponto&xCoordinate=1")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

				.andExpect(MockMvcResultMatchers.status().isConflict())
				.andExpect(MockMvcResultMatchers.content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.errorsList", Matchers.hasSize(1)))
				.andExpect(jsonPath("$.errorsList[0]", Matchers.is("coordenada y não pode ser vazia")));
	}
}
