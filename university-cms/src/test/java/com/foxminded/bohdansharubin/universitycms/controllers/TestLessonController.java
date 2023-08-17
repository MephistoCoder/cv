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

@WebMvcTest(LessonController.class)
class TestLessonController {

	@Autowired
	private MockMvc mvc;
	
	@WithMockUser("student")
	@Test
	void schedule_returnSchedulePage_correctUrl() throws Exception {
		mvc.perform(get("/user/schedule").contentType(MediaType.TEXT_HTML))
		   .andExpect(status().isOk());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"/1schedule", "/Schedule", "/schedule1"})
	void schedule_return404_incorrectUrl(String incorrectUrl) throws Exception {
		mvc.perform(get("/user" + incorrectUrl).contentType(MediaType.TEXT_HTML))
		   .andExpect(status().is4xxClientError());
	}
	
}
