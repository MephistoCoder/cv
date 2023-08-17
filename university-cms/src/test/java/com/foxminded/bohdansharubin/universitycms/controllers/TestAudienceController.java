package com.foxminded.bohdansharubin.universitycms.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.foxminded.bohdansharubin.universitycms.services.AudienceService;

@WebMvcTest(AudienceController.class)
class TestAudienceController {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private AudienceService audienceService;
	
	@WithMockUser("student")
	@Test
	void audiences_returnAudiencesPage_correctUrl() throws Exception {
		when(audienceService.findAll())
			.thenReturn(new ArrayList<>());
		mvc.perform(get("/user/audiences").contentType(MediaType.TEXT_HTML))
		   .andExpect(status().isOk());
		verify(audienceService, times(1)).findAll();
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"/audiEnces", "/1audiences", "/aud1ences"})
	void audiences_return404_incorrectUrl(String incorrectUrl) throws Exception {
		mvc.perform(get("/user" + incorrectUrl).contentType(MediaType.TEXT_HTML))
		   .andExpect(status().is4xxClientError());
	}
}
