package com.foxminded.bohdansharubin.universitycms.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UniversityController.class)
class TestUniversityController {
	
	@Autowired
	private MockMvc mvc;
	
	@WithMockUser(value = "user")
	@Test
	void home_returnHomePage_correctUrl() throws Exception {
		mvc.perform(get("/user/home").contentType(MediaType.TEXT_HTML))
		   .andExpect(status().isOk());
	}
	@WithMockUser(value = "user")
	@ParameterizedTest
	@ValueSource(strings = {"/1", "/1home", "/hom", "/home1"})
	void home_return404_incorrectUrl(String badUrl) throws Exception {
		mvc.perform(get(badUrl).contentType(MediaType.TEXT_HTML))
		   .andExpect(status().is4xxClientError());
	}
	
}
