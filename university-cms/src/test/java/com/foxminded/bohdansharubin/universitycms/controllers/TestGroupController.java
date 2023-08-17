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

import com.foxminded.bohdansharubin.universitycms.services.GroupService;

@WebMvcTest(GroupController.class)
class TestGroupController {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private GroupService groupService;
	
	@WithMockUser("student")
	@Test
	void groups_returnGroupsPage_correctUrl() throws Exception {
		when(groupService.findAll()).thenReturn(new ArrayList<>());
		mvc.perform(get("/user/groups").contentType(MediaType.TEXT_HTML))
		   .andExpect(status().isOk());
		verify(groupService, times(1)).findAll();
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"/1groups", "/Groups", "/groups1"})
	void groups_return404_incorrectUrl(String incorrectUrl) throws Exception {
		when(groupService.findAll()).thenReturn(new ArrayList<>());
		mvc.perform(get("/user" + incorrectUrl).contentType(MediaType.TEXT_HTML))
		.andExpect(status().is4xxClientError());
	}
	
}
