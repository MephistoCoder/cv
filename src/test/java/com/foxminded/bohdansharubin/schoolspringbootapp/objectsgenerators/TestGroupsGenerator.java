package com.foxminded.bohdansharubin.schoolspringbootapp.objectsgenerators;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.foxminded.bohdansharubin.schoolspringbootapp.model.Group;

class TestGroupsGenerator {
	private static final String GROUP_NAME_DEFAULT_PATTERN = "[A-Z]{2}-\\d{2}";
	private GroupsGenerator groupsGenerator = new GroupsGenerator();
	
	@Test
	void generateObjects_listOfObjectsSizeIs10_defaultValueObjectsToGenerate() {
		List<Group> generatedGroups = groupsGenerator.generateObjects();
		Assertions.assertEquals(groupsGenerator.getCountToGenerate(), generatedGroups.size());
		Assertions.assertTrue(generatedGroups.stream()
											 .allMatch(group -> group.getName()
													 				 .matches(GROUP_NAME_DEFAULT_PATTERN)));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {2, 10, 50})
	void generateObjects_listOfObjectsSizeIsAsExpected_countToGenerateIsCorrect(int countOfObjectsToGenerate) {
		groupsGenerator.setCountToGenerate(countOfObjectsToGenerate);
		List<Group> generatedGroups = groupsGenerator.generateObjects();
		Assertions.assertEquals(countOfObjectsToGenerate, generatedGroups.size());
		Assertions.assertTrue(generatedGroups.stream()
				 .allMatch(group -> group.getName()
						 				 .matches(GROUP_NAME_DEFAULT_PATTERN)));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0, -1, -10})
	void generateObjects_listOfObjectsSizeIsZero_countToGenerateIsIncorrect(int countOfObjectsToGenerate) {
		groupsGenerator.setCountToGenerate(countOfObjectsToGenerate);
		
		Assertions.assertEquals(0, groupsGenerator.generateObjects().size());
	}
	
}
