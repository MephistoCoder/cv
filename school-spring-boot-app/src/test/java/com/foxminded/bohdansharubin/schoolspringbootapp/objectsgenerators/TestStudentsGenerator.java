package com.foxminded.bohdansharubin.schoolspringbootapp.objectsgenerators;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.foxminded.bohdansharubin.schoolspringbootapp.model.Student;

class TestStudentsGenerator {
	private static final String TEST_FILE_PATH = "src/test/resources/testStudentsData.txt";
	StudentsGenerator studentsGenerator = new StudentsGenerator();
	
	@BeforeEach
	void initCorrectStudentsGenerator() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		List<String> firstNamesList = new ArrayList<>();
		firstNamesList.add("Nick");
		List<String> lastNamesList = new ArrayList<>();
		lastNamesList.add("Fury");
		Field studentsFirstNamesListField = StudentsGenerator.class.getDeclaredField("studentsFirstNamesList");
		studentsFirstNamesListField.setAccessible(true);
		studentsFirstNamesListField.set(studentsGenerator, firstNamesList);
		Field studentsLastNamesListField = StudentsGenerator.class.getDeclaredField("studentsLastNamesList");
		studentsLastNamesListField.setAccessible(true);
		studentsLastNamesListField.set(studentsGenerator, lastNamesList);
	}
	
	@Test
	void validateNamesLists_returnFalse_studentsFirstNamesListIsEmpty() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field studentsFirstNamesListField = StudentsGenerator.class.getDeclaredField("studentsFirstNamesList");
		studentsFirstNamesListField.setAccessible(true);
		studentsFirstNamesListField.set(studentsGenerator, new ArrayList<String>());
		Assertions.assertFalse(studentsGenerator.validateNamesLists());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"1John", "John1", "!John", "123", "!#$", "", " "})
	void validateNamesLists_returnFalse_studentsFirstNamesListHasInvalidString(String firstName) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		List<String> studentsFirstNamesList = new ArrayList<>();
		studentsFirstNamesList.add(firstName);
		Field studentsFirstNamesListField = StudentsGenerator.class.getDeclaredField("studentsFirstNamesList");
		studentsFirstNamesListField.setAccessible(true);
		studentsFirstNamesListField.set(studentsGenerator, studentsFirstNamesList);
		Assertions.assertFalse(studentsGenerator.validateNamesLists());
	}
	
	@Test
	void validateNamesLists_returnFalse_studentsLastNamesListIsEmpty() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field studentsLastNamesListField = StudentsGenerator.class.getDeclaredField("studentsLastNamesList");
		studentsLastNamesListField.setAccessible(true);
		studentsLastNamesListField.set(studentsGenerator, new ArrayList<String>());
		Assertions.assertFalse(studentsGenerator.validateNamesLists());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"1Doe", "Doe1", "!Doe", "123", "!#$", "", " "})
	void validateNamesLists_returnFalse_studentsLastNamesListHasInvalidString(String lastName) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		List<String> studentsLastNamesList = new ArrayList<>();
		studentsLastNamesList.add(lastName);
		Field studentsFirstNamesListField = StudentsGenerator.class.getDeclaredField("studentsFirstNamesList");
		studentsFirstNamesListField.setAccessible(true);
		studentsFirstNamesListField.set(studentsGenerator, studentsLastNamesList);
		Assertions.assertFalse(studentsGenerator.validateNamesLists());
	}
	
	@Test
	void validateNamesLists_returnTrue_listsWithCorrectData() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		List<String> studentsFirstNamesList = new ArrayList<>();
		studentsFirstNamesList.add("Tom");
		studentsFirstNamesList.add("Kate");
		Field studentsFirstNamesListField = StudentsGenerator.class.getDeclaredField("studentsFirstNamesList");
		studentsFirstNamesListField.setAccessible(true);
		studentsFirstNamesListField.set(studentsGenerator, studentsFirstNamesList);
		
		List<String> studentsLastNamesList = new ArrayList<>();
		studentsLastNamesList.add("Scott");
		studentsLastNamesList.add("Franklin");
		Field studentsLastNamesListField = StudentsGenerator.class.getDeclaredField("studentsLastNamesList");
		studentsLastNamesListField.setAccessible(true);
		studentsLastNamesListField.set(studentsGenerator, studentsLastNamesList);
		
		Assertions.assertTrue(studentsGenerator.validateNamesLists());
	}
	
	@Test
	void getDataFromFile_listsIsEmpty_fileNotExist() {
		StudentsGenerator studentsGeneratorFromFile = new StudentsGenerator("test.txt");
		studentsGeneratorFromFile.getDataFromFile(null);
		Assertions.assertTrue(studentsGeneratorFromFile.getStudentsFirstNamesList().isEmpty());
		Assertions.assertTrue(studentsGeneratorFromFile.getStudentsLastNamesList().isEmpty());
	}
	
	@Test
	void getDataFromFile_listsEqualsExpectedLists_fileExist() {
		List<String> expectedStudentsFirstNamesList = new ArrayList<>();
		expectedStudentsFirstNamesList.add("Bohdan");
		expectedStudentsFirstNamesList.add("Tom");
		
		List<String> expectedStudentsLastNamesList = new ArrayList<>();
		expectedStudentsLastNamesList.add("Scott");
		expectedStudentsLastNamesList.add("Hardi");
		
		StudentsGenerator studentsGeneratorFromFile = new StudentsGenerator(TEST_FILE_PATH);
		studentsGeneratorFromFile.getDataFromFile(TEST_FILE_PATH);
		Assertions.assertEquals(expectedStudentsFirstNamesList, studentsGeneratorFromFile.getStudentsFirstNamesList());
		Assertions.assertEquals(expectedStudentsLastNamesList, studentsGeneratorFromFile.getStudentsLastNamesList());
	}
	
	@Test
	void generateObjects_created10Students_defaultCountToGenerate() {
		StudentsGenerator studentsGeneratorFromFile = new StudentsGenerator(TEST_FILE_PATH);
		List<Student> generatedStudents = studentsGeneratorFromFile.generateObjects();
		Assertions.assertEquals(studentsGeneratorFromFile.getCountToGenerate(), generatedStudents.size());
		Assertions.assertTrue(generatedStudents.stream()
											   .anyMatch(student -> studentsGeneratorFromFile.getStudentsFirstNamesList()
													   										 .stream()
													   										 .anyMatch(firstName -> 
													   										           firstName.equals(student.getFirstName())))
							 );	
		Assertions.assertTrue(generatedStudents.stream()
											   .anyMatch(student -> studentsGeneratorFromFile.getStudentsLastNamesList()
													   										 .stream()
													   										 .anyMatch(lastName -> 
													   										           lastName.equals(student.getLastName())))
							);
	}
	
	@ParameterizedTest
	@ValueSource(ints = {2, 5, 10})
	void generateObjects_createdStudents_countToGenerateIsCorrect(int countOfObjectsToGenerate) {
		StudentsGenerator studentsGeneratorFromFile = new StudentsGenerator(TEST_FILE_PATH);
		studentsGeneratorFromFile.setCountToGenerate(countOfObjectsToGenerate);
		List<Student> generatedStudents = studentsGeneratorFromFile.generateObjects();
		Assertions.assertEquals(studentsGeneratorFromFile.getCountToGenerate(), generatedStudents.size());
		Assertions.assertTrue(generatedStudents.stream()
											   .anyMatch(student -> studentsGeneratorFromFile.getStudentsFirstNamesList()
													   										 .stream()
													   										 .anyMatch(firstName -> 
													   										           firstName.equals(student.getFirstName())))
							 );	
		Assertions.assertTrue(generatedStudents.stream()
											   .anyMatch(student -> studentsGeneratorFromFile.getStudentsLastNamesList()
													   										 .stream()
													   										 .anyMatch(lastName -> 
													   										           lastName.equals(student.getLastName())))
							);											
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0, -1, -10})
	void generateObjects_emptyObjectsList_countToGenerateIsIncorrect(int countOfObjectsToGenerate) {
		StudentsGenerator studentsGeneratorFromFile = new StudentsGenerator(TEST_FILE_PATH);
		studentsGeneratorFromFile.setCountToGenerate(countOfObjectsToGenerate);
		Assertions.assertTrue(studentsGeneratorFromFile.generateObjects().isEmpty());												
	}
	
	@Test
	void generateObjects_emptyObjectsList_dataFileNotExist() {
		StudentsGenerator studentsObjectGeneratorFromFile = new StudentsGenerator("test.txt");
		Assertions.assertTrue(studentsObjectGeneratorFromFile.generateObjects().isEmpty());												
	}
	
}
