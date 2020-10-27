package com.qa.cne.Rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.cne.persistence.domain.Badger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//Launches the Spring context (on a random port to avoid conflicts)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

//Automatically configures the MockMvc object we'll use to perform our tests
@AutoConfigureMockMvc

//@ActiveProfiles(profiles = "test")

//Executes the schema and data files to reset the Badger table BEFORE each test
@Sql(scripts = {"classpath:badger-schema.sql",
				"classpath:badger-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CnTestingIntegrationTests {

	@Autowired
	// Object provided by the Spring testing dependency to allow for easy
	// integration testing
	private MockMvc mockMVC;

	@Autowired
	// Object Spring uses to convert objects sent from/to the @Controller's to and
	// from JSON
	private ObjectMapper mapper;

	@Test
	void contextLoads() {
	}

	@Test
	void createsBadger() throws Exception {
		// Create the request body
		Badger newBadger = new Badger("fred", 21, "trees");
		// Convert request body to JSON
		String testBadgerAsJSON = this.mapper.writeValueAsString(newBadger);
		// BUILD THE TEST REQUEST
		// Method = POST
		// URL = /create
		// body = testBadgerAsJSON
		// headers = { "content-type": "application/json" }
		RequestBuilder request = post("/create").contentType(MediaType.APPLICATION_JSON).content(testBadgerAsJSON);

		// TEST THE STATUS
		// For a create method it should be 201 (CREATED)
		ResultMatcher checkStatus = status().is(201);

		// CREATE expected response body
		Badger savedBadger = new Badger(2L,"fred", 21, "trees");
		// Convert it to JSON
		String expectedAsJSON = this.mapper.writeValueAsString(savedBadger);

		// TEST THE RESPONSE BODY
		// Should match excpectedAsJSON
		ResultMatcher checkJSON = content().json(expectedAsJSON);

		// PERFORM THE TEST REQUEST
		// Sends the POST request we created earlier
		// Checks the response has the correct status code
		// Checks the response body matches our expected badger.
		this.mockMVC.perform(request).andExpect(checkStatus).andExpect(checkJSON);

		MvcResult result = this.mockMVC.perform(request).andExpect(checkStatus).andReturn();

		// to access actual result as an object
		String reqBody = result.getResponse().getContentAsString();
		Badger badgerResult = this.mapper.readValue(reqBody, Badger.class);
	}

	@Test
	void updateBadger() throws Exception {
		Badger newBadger = new Badger("jeremy", 21, "trees");
		String testBadgerAsJSON = this.mapper.writeValueAsString(newBadger);
		RequestBuilder request = put("/update/1").contentType(MediaType.APPLICATION_JSON).content(testBadgerAsJSON);

		ResultMatcher checkStatus = status().is(202);

		Badger savedBadger = new Badger("jeremy", 21, "trees");
		savedBadger.setId(1L); // id = 1 because we're updating the value inserted using data.sql

		String expectedAsJSON = this.mapper.writeValueAsString(savedBadger);
		ResultMatcher checkJSON = content().json(expectedAsJSON);

		this.mockMVC.perform(request).andExpect(checkStatus).andExpect(checkJSON);
	}

	@Test
	public void deleteBadger() throws Exception{
		this.mockMVC.perform(delete("/remove/1")).andExpect(status().isOk());
	}

	@Test
	void getBadger() throws Exception{
		Badger badger = new Badger("fred", 21, "trees");
		badger.setId(1L);
		List<Badger> badgers = new ArrayList<>();
		badgers.add(badger);
		String responseBody = this.mapper.writeValueAsString(badgers);

		this.mockMVC.perform(get("/get")).andExpect(status().isOk()).andExpect(content().json(responseBody));
	}
}
