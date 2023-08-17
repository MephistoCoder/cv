package com.foxminded.bohdansharubin.schoolspringbootapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;

import com.foxminded.bohdansharubin.schoolspringbootapp.TestConfig;
import com.foxminded.bohdansharubin.schoolspringbootapp.dao.CourseDao;
import com.foxminded.bohdansharubin.schoolspringbootapp.model.Course;
import com.foxminded.bohdansharubin.schoolspringbootapp.utils.UserInputUtils;
import com.foxminded.bohdansharubin.schoolspringbootapp.view.CourseView;

@SpringBootTest(classes = TestConfig.class)
class TestCourseService {
	
	private MockedStatic<UserInputUtils> mockedUserInput;
	
	@Mock
	private CourseDao courseDao;
	
	@Mock
	private CourseView courseView;
	
	@InjectMocks
	private CourseService courseService;
	
	@BeforeEach
	void mockUserInput() {
		mockedUserInput = mockStatic(UserInputUtils.class);
	}
	
	@AfterEach
	void closeMock() {
		mockedUserInput.close();
	}
	
	@Test
	void getAllCourses_returnCourseList_coursesExistsInTable() {
		List<Course> expectedCourses = new ArrayList<>();
		expectedCourses.add(new Course("Math", "test"));
		expectedCourses.add(new Course("IT", "test"));
		when(courseDao.findAll())
			   .thenReturn(expectedCourses);
		List<Course> actualCourses = courseService.getAllCourses();
		assertEquals(expectedCourses, actualCourses);
	}
	
	@Test
	void getAllCourses_returnEmptyList_coursesNotExistsInTable() {
		when(courseDao.findAll())
		   .thenReturn(new ArrayList<>());
		List<Course> actualCourses = courseService.getAllCourses();
		assertTrue(actualCourses.isEmpty());
	}
	
	@Test
	void findCourseById_returnCourseWithSuchId_courseWithIdExistsInTable() {
		mockedUserInput.when(UserInputUtils::getIntegerInputFromUser)
						.thenReturn(1);
		Course expectedCourse = new Course("Math", "test");
		expectedCourse.setId(1);
		when(courseDao.getReferenceById(1))
			.thenReturn(expectedCourse);
		assertEquals(expectedCourse, courseService.findCourseById());
	}
	
	@Test
	void findCourseById_returnNull_courseWithIdNotExistsInTable() {
		mockedUserInput.when(UserInputUtils::getIntegerInputFromUser)
						.thenReturn(1);
		when(courseDao.getReferenceById(1))
			.thenReturn(null);
		assertNull(courseService.findCourseById());
	}
	
	@Test
	void findCourseByName_returnNull_courseWithNameNotExistsInTable() {
		mockedUserInput.when(UserInputUtils::getStringInputFromUser)
						.thenReturn("test");
		when(courseDao.findByName("test"))
			.thenReturn(Optional.empty());
		assertNull(courseService.findCourseByName());
	}
	
	@Test
	void findCourseByName_returnCourseWithSuchName_courseWithNameExistsInTable() {
		mockedUserInput.when(UserInputUtils::getStringInputFromUser)
						.thenReturn("Math");
		Course expectedCourse = new Course("Math", "test");
		expectedCourse.setId(1);
		when(courseDao.findByName("Math"))
			.thenReturn(Optional.of(expectedCourse));
		assertEquals(expectedCourse, courseService.findCourseByName());
	}
	
}
