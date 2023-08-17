package com.foxminded.bohdansharubin.schoolspringbootapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;

import com.foxminded.bohdansharubin.schoolspringbootapp.TestConfig;
import com.foxminded.bohdansharubin.schoolspringbootapp.dao.GroupDao;
import com.foxminded.bohdansharubin.schoolspringbootapp.model.Group;
import com.foxminded.bohdansharubin.schoolspringbootapp.model.Student;
import com.foxminded.bohdansharubin.schoolspringbootapp.utils.UserInputUtils;

@SpringBootTest(classes = TestConfig.class)
class TestGroupService {
	private MockedStatic<UserInputUtils> mockedUserInput;
	
	@Mock
	private GroupDao groupDao;
		
	@InjectMocks
	private GroupService groupService;
	
	@BeforeEach
	void mockUserInput() {
		mockedUserInput = mockStatic(UserInputUtils.class);
	}
	
	@AfterEach
	void closeUserInput() {
		mockedUserInput.close();
	}
	
	@Test
	void getAllGroups_returnGroupsList_groupsExistInTable() {
		List<Group> expectedGroups = new ArrayList<>();
		expectedGroups.add(new Group("SA-12"));
		expectedGroups.add(new Group("GD-46"));
		when(groupDao.findAll())
			.thenReturn(expectedGroups);
		assertEquals(expectedGroups, groupService.getAllGroups());
	}
	
	@Test
	void getAllGroups_returnEmptyList_groupsNotExistInTable() {
		when(groupDao.findAll())
			.thenReturn(new ArrayList<>());
		List<Group> actualGroups = groupService.getAllGroups();
		assertTrue(actualGroups.isEmpty());
	}
	
	@Test
	void getGroupsByMaxNumberOfStudents_returnEmptyList_groupsHaveMoreThanExpectedStudents() {
		mockedUserInput.when(UserInputUtils::getIntegerInputFromUser)
					   .thenReturn(5);
		when(groupDao.findAll())
			.thenReturn(new ArrayList<>());
		List<Group> actualGroups = groupService.getAllGroups();
		assertTrue(actualGroups.isEmpty());
	}
	
	@Test
	void getGroupsByMaxNumberOfStudents_returnGroupsList_groupsHaveMoreThanExpectedStudents() {
		mockedUserInput.when(UserInputUtils::getIntegerInputFromUser)
					   .thenReturn(5);
		List<Group> expectedGroups = new ArrayList<>();
		Group group = new Group();
		group.setStudents(Arrays.asList(new Student(), new Student(), new Student()));
		expectedGroups.add(group);
		when(groupDao.findAll())
					 .thenReturn(expectedGroups);
		List<Group> actualGroups = groupService.getAllGroups();
		assertEquals(expectedGroups, actualGroups);
	}
	
}
