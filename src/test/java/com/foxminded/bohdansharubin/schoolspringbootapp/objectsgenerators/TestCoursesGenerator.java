package com.foxminded.bohdansharubin.schoolspringbootapp.objectsgenerators;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.foxminded.bohdansharubin.schoolspringbootapp.model.Course;

class TestCoursesGenerator {
	private static final String TEST_FILE_PATH = "src/test/resources/testCoursesData.txt";
	private static final String EMPTY_FILE_PATH = "src/test/resources/emptyFile.txt";
	private CoursesGenerator coursesGenerator = new CoursesGenerator();
	
	List<String> getCourseNamesFromTestFile() {
		try {
			return Files.readAllLines(Paths.get(TEST_FILE_PATH));
		} catch (IOException e) {
			return new ArrayList<>();
		}
	}
	
	@Test
	void validateCoursesNamesList_returnFalse_namesListIsEmpty() {
		Assertions.assertFalse(coursesGenerator.validateCoursesNamesList());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"1Biology", "", "1234"})
	void validateCoursesNamesList_returnFalse_dataIsIncorrect(String testData) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		List<String> testCoursesNamesList = new ArrayList<>();
		testCoursesNamesList.add(testData);
		Field coursesNamesListField = CoursesGenerator.class.getDeclaredField("coursesNamesList");
		coursesNamesListField.setAccessible(true);
		coursesNamesListField.set(coursesGenerator, testCoursesNamesList);
		Assertions.assertFalse(coursesGenerator.validateCoursesNamesList());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"Biology", "math", "IT"})
	void validateCoursesNamesList_returnTrue_dataIsCorrect(String testData) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		List<String> testCoursesNamesList = new ArrayList<>();
		testCoursesNamesList.add(testData);
		Field coursesNamesListField = CoursesGenerator.class.getDeclaredField("coursesNamesList");
		coursesNamesListField.setAccessible(true);
		coursesNamesListField.set(coursesGenerator, testCoursesNamesList);
		Assertions.assertTrue(coursesGenerator.validateCoursesNamesList());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"test.txt", EMPTY_FILE_PATH})
	void generateObjects_objectsListIsEmpty_fileIsEmptyOrFileNotExist(String filePath) {
		CoursesGenerator coursesGeneratorFromFile = new CoursesGenerator(filePath);
		Assertions.assertTrue(coursesGeneratorFromFile.generateObjects().isEmpty());
	}
	
	@Test
	void generateObjects_createdOneCourse_defaultCountToGenerate() {
		CoursesGenerator coursesGeneratorFromFile = new CoursesGenerator(TEST_FILE_PATH);
		List<Course> generatedCourses = coursesGeneratorFromFile.generateObjects();
		Assertions.assertEquals(coursesGeneratorFromFile.getCountToGenerate(),
								generatedCourses.size());	
		generatedCourses.forEach(course -> 
								 Assertions.assertTrue(getCourseNamesFromTestFile().stream()
																				   .anyMatch(courseName -> 
																				   			 courseName.equals(course.getName())))
								);
	}
	
	@ParameterizedTest
	@ValueSource(ints = {2, 3})
	void generateObjects_createdCourses_countToGenerateIsCorrect(int countOfObjectsToGenerate) {
		CoursesGenerator coursesGeneratorFromFile = new CoursesGenerator(TEST_FILE_PATH);
		coursesGeneratorFromFile.setCountToGenerate(countOfObjectsToGenerate);
		List<Course> generatedCourses = coursesGeneratorFromFile.generateObjects();
		Assertions.assertEquals(countOfObjectsToGenerate, generatedCourses.size());
		Assertions.assertTrue(generatedCourses.stream()
									   		  .allMatch(course -> course instanceof Course)
							 );					
		generatedCourses.forEach(course -> 
								 Assertions.assertTrue(getCourseNamesFromTestFile().stream()
																				   .anyMatch(courseName -> 
																				   			 courseName.equals(course.getName())))
								);
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0, -1, -10})
	void generateObjects_emptyObjectsList_countToGenerateIsIncorrect(int countOfObjectsToGenerate) {
		CoursesGenerator coursesGeneratorFromFile = new CoursesGenerator(TEST_FILE_PATH);
		coursesGeneratorFromFile.setCountToGenerate(countOfObjectsToGenerate);
		Assertions.assertTrue(coursesGeneratorFromFile.generateObjects().isEmpty());												
	}
	
	@ParameterizedTest
	@ValueSource(ints = {5, 10})
	void generateObjects_coursesListIsEmpty_namesOfCoursesLessThenCountOfObjectsToGenerate(int countOfObjectsToGenerate) {
		CoursesGenerator coursesGeneratorFromFile = new CoursesGenerator(TEST_FILE_PATH);
		coursesGeneratorFromFile.setCountToGenerate(countOfObjectsToGenerate);
		Assertions.assertTrue(coursesGeneratorFromFile.generateObjects().isEmpty());												
	}
	
}
