package com.foxminded.bohdansharubin.schoolspringbootapp.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.jdbc.Sql;

import com.foxminded.bohdansharubin.schoolspringbootapp.model.Course;

@DataJpaTest
@EnableAutoConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableJpaRepositories(basePackages ="com.foxminded.bohdansharubin.schoolspringbootapp.dao")
@EntityScan(basePackages = "com.foxminded.bohdansharubin.schoolspringbootapp.model")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestCourseDao {
	
	@Autowired
	private CourseDao courseDao;
	
	@Sql("/scripts/courses/drop_courses_data.sql")
	@Test
	@Order(1)
	void getAll_returnEmptyList_tableHasNoEntries() {
		assertEquals(0, courseDao.findAll()
								 .size());	
	}
	
	@Sql("/scripts/courses/insert_courses.sql")
	@Test
	@Order(2)
	void getAll_returnListWithCourses_tableHasEntries() {
		List<Course> expectedCourses = new ArrayList<>();
		Course course = new Course("Math", "test");
		course.setId(1);
		expectedCourses.add(course);
		course = new Course("IT", "test");
		course.setId(2);
		expectedCourses.add(course);
		List<Course> actualCourses = courseDao.findAll();
		assertEquals(expectedCourses.size(), actualCourses.size());
		assertEquals(expectedCourses, actualCourses);
	}
	
	@ParameterizedTest
	@CsvSource({"English, test", "Java, test"})
	@Order(3)
	void add_addedCourseExistsInTable_entryNotExistsInTable(String name,
			 												String description) {
		Course expectedCourse = new Course(name, description);
		courseDao.save(expectedCourse);
		Course actualCourse = courseDao.getReferenceById(expectedCourse.getId());
		assertEquals(expectedCourse, actualCourse);
	}
	
	@ParameterizedTest
	@CsvSource({"English, test", "Java, test"})
	@Order(4)
	void getById_returnPresentCourseOptional_entryWithSuchIdExistsInTable(String name,
																		  String description) {
		Course expectedCourse = new Course(name, description);
		courseDao.save(expectedCourse);
		Optional<Course> actualCourse = Optional.ofNullable(courseDao.getReferenceById(expectedCourse.getId()));
		assertTrue(actualCourse.isPresent());
		assertEquals(expectedCourse, actualCourse.get());
	}
	
	@ParameterizedTest
	@CsvSource({"Math, test", "IT, test"})
	@Order(5)
	void getByName_returnPresentCourseOptional_entryWithSuchNameExistsInTable(String name,
																  			  String description) {
		Course expectedCourse = new Course(name, description);
		courseDao.save(expectedCourse);
		Optional<Course> actualCourse = courseDao.findByName(name);
		assertTrue(actualCourse.isPresent());
		assertEquals(expectedCourse, actualCourse.get());
	}
	
	@Test
	@Order(6)
	void getByName_returnEmptyOptional_entryWithSuchNameNotExistsInTable() {
		assertEquals(Optional.empty(), courseDao.findByName("Test")); 
	}
	
	@ParameterizedTest
	@CsvSource({"Math, test", "IT, test"})
	@Order(7)
	void deleteById_deletedCourseNotExistsInTable_entryWithSuchExistsInTable(String name,
																			 String description) {
		Course expectedCourse = new Course(name, description);
		courseDao.save(expectedCourse);
		assertNotNull(courseDao.getReferenceById(expectedCourse.getId()));
		courseDao.deleteById(expectedCourse.getId());
		assertThrows(JpaObjectRetrievalFailureException.class, () -> courseDao.getReferenceById(expectedCourse.getId()));
	}
	
	
}
