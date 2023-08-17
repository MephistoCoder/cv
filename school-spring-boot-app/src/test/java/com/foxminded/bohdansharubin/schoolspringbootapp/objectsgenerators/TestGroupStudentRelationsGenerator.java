package com.foxminded.bohdansharubin.schoolspringbootapp.objectsgenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.foxminded.bohdansharubin.schoolspringbootapp.model.Group;
import com.foxminded.bohdansharubin.schoolspringbootapp.model.Student;

class TestGroupStudentRelationsGenerator {
	
	private GroupStudentRelationsGenerator groupStudentRelationsGenerator = new GroupStudentRelationsGenerator();

	@BeforeEach
	void initCorrectGroupStudentRelationsGenerator() {
		List<Student> students = new ArrayList<>();
		List<Group> groups = new ArrayList<>();
		groups.add(new Group("IA-21"));
		IntStream.range(0, groupStudentRelationsGenerator.getMaxStudentsInGroup())
				 .forEach(i -> students.add(new Student("Bohdan", "Bohdan")));
		groupStudentRelationsGenerator.setGroups(groups);
		groupStudentRelationsGenerator.setStudents(students);
	}
	
	@Test
	void validateGenerationParameters_returnFalse_groupsListIsNull() {
		List<Group> testGroupsList = null;
		groupStudentRelationsGenerator.setGroups(testGroupsList);
		Assertions.assertFalse(groupStudentRelationsGenerator.validateGenerationParameters());
	}

	@Test
	void validateGenerationParameters_returnFalse_studentsListIsNull() {
		List<Student> testStudentsList = null;
		groupStudentRelationsGenerator.setStudents(testStudentsList);
		Assertions.assertFalse(groupStudentRelationsGenerator.validateGenerationParameters());
	}

	@Test
	void validateGenerationParameters_returnFalse_groupsListIsEmpty() {
		List<Group> testGroupsList = new ArrayList<>();
		groupStudentRelationsGenerator.setGroups(testGroupsList);
		Assertions.assertFalse(groupStudentRelationsGenerator.validateGenerationParameters());
	}

	@Test
	void validateGenerationParameters_returnFalse_studentsListIsEmpty() {
		List<Student> testStudentsList = new ArrayList<>();
		groupStudentRelationsGenerator.setStudents(testStudentsList);
		Assertions.assertFalse(groupStudentRelationsGenerator.validateGenerationParameters());
	}

	@ParameterizedTest
	@ValueSource(ints = {-1, -7, -10})
	void validateGenerationParameters_returnFalse_minStudentsInGroupLessThenZero(int minStudentsInGroup) {
		List<Student> testStudentsList = new ArrayList<>();
		IntStream.range(0, 2)
				 .forEach(i -> testStudentsList.add(null));
		groupStudentRelationsGenerator.setStudents(testStudentsList);
		groupStudentRelationsGenerator.setMinStudentsInGroup(minStudentsInGroup);
		Assertions.assertFalse(groupStudentRelationsGenerator.validateGenerationParameters()); 
	}
	
	@ParameterizedTest
	@CsvSource({"0, -5", "1, 0", "10, 5"})
	void validateGenerationParameters_returnFalse_maxStudentsInGroupLessThenMinStudentsInGroup(int minStudentsInGroup, 
																								int maxStudentsInGroup) {
		List<Student> testStudentsList = new ArrayList<>();
		IntStream.range(0, 12)
				 .forEach(i -> testStudentsList.add(null));
		groupStudentRelationsGenerator.setStudents(testStudentsList);
		groupStudentRelationsGenerator.setMinStudentsInGroup(minStudentsInGroup);
		groupStudentRelationsGenerator.setMaxStudentsInGroup(maxStudentsInGroup);
		Assertions.assertFalse(groupStudentRelationsGenerator.validateGenerationParameters());
	}
	
	@ParameterizedTest
	@ValueSource(ints = {2, 5, 10})
	void validateGenerationParameters_returnFalse_studentsListSizeLessThenMinStudentsInGroup(int minStudentsInGroup) {
		List<Student> testStudentsList = new ArrayList<>();
		testStudentsList.add(null);
		groupStudentRelationsGenerator.setStudents(testStudentsList);
		groupStudentRelationsGenerator.setMinStudentsInGroup(minStudentsInGroup);
		Assertions.assertFalse(groupStudentRelationsGenerator.validateGenerationParameters());
	}
	
	@ParameterizedTest
	@ValueSource(ints = {2, 5, 10})
	void validateGenerationParameters_returnFalse_studentsListSizeLessThenMaxStudentsInGroup(int maxStudentsInGroup) {
		List<Student> testStudentsList = new ArrayList<>();
		testStudentsList.add(null);
		groupStudentRelationsGenerator.setStudents(testStudentsList);
		groupStudentRelationsGenerator.setMaxStudentsInGroup(maxStudentsInGroup);
		Assertions.assertFalse(groupStudentRelationsGenerator.validateGenerationParameters());
	}

	@Test
	void validateGenerationParameters_returnTrue_correctGenerationParameters() {
		List<Group> testGroupsList = new ArrayList<>();
		List<Student> testStudentsList = new ArrayList<>();
		IntStream.range(0, 40).forEach(i -> {
			testGroupsList.add(null);
			testStudentsList.add(null);
		});
		groupStudentRelationsGenerator.setGroups(testGroupsList);
		groupStudentRelationsGenerator.setStudents(testStudentsList);
		Assertions.assertTrue(groupStudentRelationsGenerator.validateGenerationParameters());
	}

	@Test
	void generateDependencies_returnStudentsWithGroup_studentsGroupsListsAndParametersCorrect() {
		List<Group> testGroupsList = new ArrayList<>();
		List<Student> testStudentsList = new ArrayList<>();
		testGroupsList.add(new Group("JS-34"));
		testGroupsList.add(new Group("JS-31"));
		testGroupsList.add(new Group("JS-32"));
		testStudentsList.add(new Student("John", "Smith"));
		testStudentsList.add(new Student("Adam", "Sendler"));
		groupStudentRelationsGenerator.setGroups(testGroupsList);
		groupStudentRelationsGenerator.setStudents(testStudentsList);
		groupStudentRelationsGenerator.setMinStudentsInGroup(1);
		groupStudentRelationsGenerator.setMaxStudentsInGroup(2);
		groupStudentRelationsGenerator.generateDependencies();
		Assertions.assertTrue(groupStudentRelationsGenerator.getStudents()
															.stream()
															.anyMatch(student -> student.getGroup() != null)
							);
	}

}
