package com.foxminded.bohdansharubin.universitycms.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

import com.foxminded.bohdansharubin.universitycms.dto.CourseDTO;
import com.foxminded.bohdansharubin.universitycms.models.Roles;
import com.foxminded.bohdansharubin.universitycms.services.CourseService;
import com.foxminded.bohdansharubin.universitycms.services.TeacherService;

@WebMvcTest(CourseController.class)
class TestCourseController {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private CourseService courseService;
	
	@MockBean
	private TeacherService teacherService;
	
	@WithMockUser("student")
	@Test
	void courses_returnCoursesPage_correctUrl() throws Exception {
		when(courseService.findAll())
			.thenReturn(new ArrayList<>());
		mvc.perform(get("/user/courses")
				.contentType(MediaType.TEXT_HTML)
				.flashAttr("authority", Roles.ROLE_ADMIN))
		   .andExpect(status().isOk());
		verify(courseService, times(1)).findAll();
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"/1courses", "/Courses", "/courses1"})
	void courses_return404_incorrectUrl(String incorrectUrl) throws Exception {
		mvc.perform(get("/user" + incorrectUrl).contentType(MediaType.TEXT_HTML))
		   .andExpect(status().is4xxClientError());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"/user/edit_course", "/user/Edit-course", "/user/edit-courses"})
	void editCourses_return404_incorrectUrl(String incorrectUrl) throws Exception {
		mvc.perform(post("/user" + incorrectUrl).contentType(MediaType.TEXT_HTML))
			.andExpect(status().is4xxClientError());
	}
	
	@Test
	@WithMockUser(authorities = "ADMIN")
	void editCourses_returnEditCoursePage_userHasAuthorityAdmin() throws Exception {
		mvc.perform(post("/user/edit-course")
				.contentType(MediaType.TEXT_HTML)
				.flashAttr("courseDTO", new CourseDTO())
				.with(csrf()))
			.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(authorities = {"STUDENT", "TEACHER"})
	void delete_return405_HasNoAuthorityAdmin() throws Exception {
		mvc.perform(post("/user/delete-course")
				.contentType(MediaType.TEXT_HTML)
				.requestAttr("id", 1)
				.with(csrf()))
			.andExpect(status().isMethodNotAllowed());
	}
	
	@Test
	@WithMockUser(authorities = "ADMIN")
	void delete_redirectionCoursesPageWithoutDeletionCourse_HasAuthorityAdmin() throws Exception {
		doNothing().when(courseService)
			.deleteById(anyInt());
		mvc.perform(delete("/user/delete-course")
				.flashAttr("id", Integer.valueOf(1))
				.requestAttr("id", 1)
				.with(csrf()))
			.andExpect(status().is3xxRedirection());
//		verify(courseService, times(1)).deleteById(anyInt());
	}
	
	@Test
	@WithMockUser(authorities = "ADMIN")
	void save_redirectionCoursesPage_HasAuthorityAdmin() throws Exception {
		mvc.perform(post("/user/save-course")
				.contentType(MediaType.TEXT_HTML)
				.flashAttr("courseDTO", new CourseDTO())
				.with(csrf()))
			.andExpect(status().is3xxRedirection());
		verify(courseService, times(1)).save(any());
	}
	
	@Test
	@WithMockUser(authorities = "ADMIN")
	void update_redirectionCoursesPage_HasAuthorityAdmin() throws Exception {
		mvc.perform(post("/user/update-course")
				.contentType(MediaType.TEXT_HTML)
				.flashAttr("courseDTO", new CourseDTO())
				.with(csrf()))
		.andExpect(status().is3xxRedirection());
		verify(courseService, times(1)).update(any());
	}
	
}
