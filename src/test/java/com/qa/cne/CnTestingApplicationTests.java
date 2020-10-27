package com.qa.cne;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.cne.persistence.domain.Badger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CnTestingApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void contextLoads() {
	}

	@Test
	public void createsBadger() throws Exception {
		Badger newBadger = new Badger("fred", 21, "trees");
		String testBadgerAsJSON = this.mapper.writeValueAsString(newBadger);
		RequestBuilder request = post("/create").contentType(MediaType.APPLICATION_JSON).content(testBadgerAsJSON);

		ResultMatcher checkStatus = status().is(201);

		Badger savedBadger = new Badger("fred", 21, "trees");
		savedBadger.setId(3L); // value as 2 because ID 1 should have been inserted with .sql script

		String expectedAsJSON = this.mapper.writeValueAsString(savedBadger);
		ResultMatcher checkJSON = content().json(expectedAsJSON);

		this.mockMvc.perform(request).andExpect(checkStatus).andExpect(checkJSON);

		MvcResult result = this.mockMvc.perform(request).andExpect(checkStatus).andReturn();

		// to access actual result as an object
		String reqBody = result.getResponse().getContentAsString();

		Badger badgerResult = this.mapper.readValue(reqBody, Badger.class);
	}

//	@Test
//	public void updateBadger() throws Exception {
//
//	}
//
//	@Test
//	public void deleteBadger() throws Exception{
//		this.mockMvc.perform(delete("/remove/1")).andExpect(status().isOk());
//	}

}
