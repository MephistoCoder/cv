package com.foxminded.bohdansharubin.schoolspringbootapp.objectsgenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.foxminded.bohdansharubin.schoolspringbootapp.model.Course;
import com.foxminded.bohdansharubin.schoolspringbootapp.model.Student;

class TestCourseStudentRelationsGenerator {

	private CourseStudentRelationsGenerator courseStudentRelationsGenerator = new CourseStudentRelationsGenerator();

	@Test
	void validateGenerationParameters_returnFalse_coursesListIsNull() {
		List<Course> testCoursesList = null;
		courseStudentRelationsGenerator.setCourses(testCoursesList);
		Assertions.assertFalse(courseStudentRelationsGenerator.validateGenerationParameters());
	}

	@Test
	void validateGenerationParameters_returnFalse_studentsListIsNull() {
		List<Student> testStudentsList = null;
		courseStudentRelationsGenerator.setStudents(testStudentsList);
		Assertions.assertFalse(courseStudentRelationsGenerator.validateGenerationParameters());
	}

	@Test
	void validateGenerationParameters_returnFalse_coursesListIsEmpty() {
		List<Course> testCoursesList = new ArrayList<>();
		courseStudentRelationsGenerator.setCourses(testCoursesList);
		Assertions.assertFalse(courseStudentRelationsGenerator.validateGenerationParameters());
	}

	@Test
	void validateGenerationParameters_returnFalse_studentsListIsEmpty() {
		List<Student> testStudentsList = new ArrayList<>();
		courseStudentRelationsGenerator.setStudents(testStudentsList);
		Assertions.assertFalse(courseStudentRelationsGenerator.validateGenerationParameters());
	}

	@ParameterizedTest
	@ValueSource(ints = {0, -1, -10})
	void validateGenerationParameters_returnFalse_minCoursesForStudentLessThenZero(int minCoursesForStudent) {
		List<Course> testCoursesList = new ArrayList<>();
		IntStream.range(0, 2)
				 .forEach(i -> testCoursesList.add(null));
		courseStudentRelationsGenerator.setCourses(testCoursesList);
		courseStudentRelationsGenerator.setMinCoursesForStudent(minCoursesForStudent);
		Assertions.assertFalse(courseStudentRelationsGenerator.validateGenerationParameters());
	}
	
	@ParameterizedTest
	@CsvSource({"0, -5", "1, 0", "10, 5"})
	void validateGenerationParameters_returnFalse_maxCoursesForStudentLessThenMinCoursesForStudent(int minCoursesForStudent, 
																								   int maxCoursesForStudent) {
		List<Course> testCoursesList = new ArrayList<>();
		IntStream.range(0, 12)
				 .forEach(i -> testCoursesList.add(null));
		courseStudentRelationsGenerator.setCourses(testCoursesList);
		courseStudentRelationsGenerator.setMinCoursesForStudent(minCoursesForStudent);
		courseStudentRelationsGenerator.setMaxCoursesForStudent(maxCoursesForStudent);
		Assertions.assertFalse(courseStudentRelationsGenerator.validateGenerationParameters());
	}
	
	@ParameterizedTest
	@ValueSource(ints = {2, 5, 10})
	void validateGenerationParameters_returnFalse_coursesListSizeLessThenMinCoursesForStudent(int minCoursesForStudent) {
		List<Course> testCoursesList = new ArrayList<>();
		testCoursesList.add(null);
		courseStudentRelationsGenerator.setCourses(testCoursesList);
		courseStudentRelationsGenerator.setMinCoursesForStudent(minCoursesForStudent);
		Assertions.assertFalse(courseStudentRelationsGenerator.validateGenerationParameters());
	}
	
	@ParameterizedTest
	@ValueSource(ints = {2, 5, 10})
	void validateGenerationParameters_returnFalse_coursesListSizeLessThenMaxCoursesForStudent(int maxCoursesForStudent) {
		List<Course> testCoursesList = new ArrayList<>();
		testCoursesList.add(null);
		courseStudentRelationsGenerator.setCourses(testCoursesList);
		courseStudentRelationsGenerator.setMaxCoursesForStudent(maxCoursesForStudent);
		Assertions.assertFalse(courseStudentRelationsGenerator.validateGenerationParameters());
	}

	@Test
	void validateGenerationParameters_returnTrue_correctGenerationParameters() {
		List<Course> testCoursesList = new ArrayList<>();
		List<Student> testStudentsList = new ArrayList<>();
		IntStream.range(0, 40).forEach(i -> {
			testCoursesList.add(null);
			testStudentsList.add(null);
		});
		courseStudentRelationsGenerator.setCourses(testCoursesList);
		courseStudentRelationsGenerator.setStudents(testStudentsList);
		Assertions.assertTrue(courseStudentRelationsGenerator.validateGenerationParameters());
	}

	@Test
	void generateDependencies_returnStudentsWithCourses_studentsCourseListsAndParametersCorrect() {
		List<Course> testCoursesList = new ArrayList<>();
		List<Student> testStudentsList = new ArrayList<>();
		testCoursesList.add(new Course("Math", ""));
		testCoursesList.add(new Course("PE", ""));
		testCoursesList.add(new Course("IT", ""));
		testStudentsList.add(new Student("John", "Smith"));
		testStudentsList.add(new Student("Adam", "Sendler"));
		courseStudentRelationsGenerator.setCourses(testCoursesList);
		courseStudentRelationsGenerator.setStudents(testStudentsList);
		courseStudentRelationsGenerator.generateDependencies();
		Assertions.assertTrue(testStudentsList.stream()
											  .noneMatch(student -> student.getCourses().isEmpty()));
	}

}
