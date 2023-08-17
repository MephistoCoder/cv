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

import com.foxminded.bohdansharubin.universitycms.services.StudentService;

@WebMvcTest(StudentController.class)
public class TestStudentController {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private StudentService studentService;
	
	@WithMockUser("student")
	@Test
	void students_returnStudentsPage_correctUrl() throws Exception {
		when(studentService.findAll()).thenReturn(new ArrayList<>());
		mvc.perform(get("/user/students").contentType(MediaType.TEXT_HTML))
		   .andExpect(status().isOk());
		verify(studentService, times(1)).findAll();
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"/1students", "/Students", "/students1"})
	void students_return404_incorrectUrl(String incorrectUrl) throws Exception {
		mvc.perform(get("/user" + incorrectUrl).contentType(MediaType.TEXT_HTML))
		   .andExpect(status().is4xxClientError());
	}
	
}
