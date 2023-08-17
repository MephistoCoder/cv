package com.foxminded.bohdansharubin.universitycms.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.foxminded.bohdansharubin.universitycms.dto.UserDTO;
import com.foxminded.bohdansharubin.universitycms.models.Roles;
import com.foxminded.bohdansharubin.universitycms.services.UserService;

@WebMvcTest(AdminController.class)
class TestAdminController {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UserService userService;
		
	@WithMockUser("admin")
	@Test
	void admin_returnAdminPage_correctUrl() throws Exception {
		when(userService.findAll())
			.thenReturn(new ArrayList<>());
		mvc.perform(get("/admin-panel")
				.contentType(MediaType.TEXT_HTML)
				.flashAttr("userDTO", new UserDTO()))
		   .andExpect(status().isOk());
		verify(userService, times(1)).findAll();
	}
	
	@WithMockUser(authorities = "ADMIN")
	@Test
	void editUser_returnEditUserPage_correctUrl() throws Exception {
		UserDTO testDto = new UserDTO();
		testDto.setId(1);
		testDto.setUsername("Test");
		testDto.setPassword("Test");
		testDto.setRole(Roles.ROLE_ADMIN);
		
		mvc.perform(post("/edit-user")
				.contentType(MediaType.TEXT_HTML)
				.flashAttr("userDTO", testDto)
				.with(csrf()))
		.andExpect(status().isOk());
	}
	
	@WithMockUser("admin")
	@ParameterizedTest
	@ValueSource(strings = {"/adminPanel", "/admin_panel", "/Admin-panel"})
	void admin_return404_incorrectUrl(String badUrl) throws Exception {
		mvc.perform(get("/admin" + badUrl).contentType(MediaType.TEXT_HTML))
			.andExpect(status().is4xxClientError());
	}
	
	@WithMockUser("admin")
	@ParameterizedTest
	@ValueSource(strings = {"/editUser", "/edit_user", "/Edit-User"})
	void editUser_returnEdituserPage_incorrectUrl(String badUrl) throws Exception {
		mvc.perform(get("/admin" + badUrl).contentType(MediaType.TEXT_HTML))
			.andExpect(status().is4xxClientError());
	}
	
}
