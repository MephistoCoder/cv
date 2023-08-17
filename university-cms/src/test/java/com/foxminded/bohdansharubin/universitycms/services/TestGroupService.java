package com.foxminded.bohdansharubin.universitycms.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foxminded.bohdansharubin.universitycms.models.Group;
import com.foxminded.bohdansharubin.universitycms.repositories.GroupRepository;

@ExtendWith(MockitoExtension.class)
class TestGroupService {
	
	@Mock
	GroupRepository groupRepository;
	
	@InjectMocks
	GroupService groupService;
	
	@ParameterizedTest
	@ValueSource(strings = {"AA-11", "QT-51", "QW-12/1"})
	void save_returnGroup_nameIsCorrect(String name) {
		when(groupRepository.save(any(Group.class)))
			.thenReturn(Group.builder()
							 .name(name)
							 .build());
		
		Optional<Group> actualGroup = groupService.save(name);
		
		verify(groupRepository, times(1)).save(any(Group.class));
		assertTrue(actualGroup.isPresent());
		assertEquals(name, actualGroup.get()
									  .getName());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"Aa-11", "QT-a1", "QTQ-12", "RE-12\1", "GD-12/12"})
	void save_returnOptionalEmpty_nameIsIncorrect(String name) {
		assertFalse(groupService.save(name).isPresent());
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1, 2, 12})
	void findById_returnOptionalGroup_groupWithIdExists(int id) {
		when(groupRepository.getReferenceById(id))
			.thenReturn(Group.builder()
								.id(id)
								.name("TE-12")
								.build());
		
		Optional<Group> actualGroup = groupService.findById(id);
		
		verify(groupRepository, times(1)).getReferenceById(id);
		assertTrue(actualGroup.isPresent());
		assertEquals(id, actualGroup.get()
									   .getId());	
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1, 2, 12})
	void findById_returnOptionalEmpty_groupWithIdNotExists(int id) {
		when(groupRepository.getReferenceById(id))
			.thenReturn(null);
				
		assertFalse(groupService.findById(id).isPresent());	
		verify(groupRepository, times(1)).getReferenceById(id);
	}
	
	@Test
	void findAll_returnEmptyList_groupsNotExist() {
		when(groupRepository.findAll())
			.thenReturn(new ArrayList<>());
		
		assertTrue(groupService.findAll().isEmpty());	
		
		verify(groupRepository, times(1)).findAll();
	}
	
	@Test
	void findById_returnGroupList_groupsExist() {
		List<Group> expectedGroups = new ArrayList<>();
		expectedGroups.add(Group.builder()
								.id(1)
								.name("SF-12")
								.build());
		expectedGroups.add(Group.builder()
					  .id(2)
					  .name("SA-12")
					  .build());
		
		when(groupRepository.findAll())
			.thenReturn(expectedGroups);
		
		List<Group> actualGroups = groupService.findAll();
		
		verify(groupRepository, times(1)).findAll();
		assertFalse(actualGroups.isEmpty());
		assertEquals(expectedGroups, actualGroups);
	}
	
	@ParameterizedTest
	@NullSource
	void findByName_returnEmptyOptional_nameIsNull(String name) {
		assertFalse(groupService.findByName(name)
								.isPresent());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"MA-12", "IT-12/1"})
	void findByName_returnOptionalGroup_nameIsCorrect(String name) {
		when(groupRepository.findByName(name))
			.thenReturn(Optional.of(Group.builder()
										 .id(1)
										 .name(name)
										 .build()));
		
		Optional<Group> actualGroup = groupService.findByName(name);
		
		verify(groupRepository, times(1)).findByName(name);
		assertTrue(actualGroup.isPresent());
		assertEquals(name, actualGroup.get()
									  .getName());
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-1, 0})
	void update_returnOptionalEmpty_groupNotFoundById(int id) {
		when(groupRepository.existsById(anyInt()))
			.thenReturn(false);
		
		assertFalse(groupService.update(id, "TE-11").isPresent());
		verify(groupRepository, times(1)).existsById(anyInt());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"Aa-11", "QT-a1", "QTQ-12"})
	void update_returnOptionalEmpty_nameIsIncorrect(String name) {
		when(groupRepository.existsById(anyInt()))
			.thenReturn(true);
		
		assertFalse(groupService.update(1 ,name).isPresent());
	}
	
	@ParameterizedTest
	@CsvSource({"1, TW-12", "2, GW-53"})
	void update_returnOptionalGroup_correctData(int id, String name) {
		when(groupRepository.existsById(anyInt()))
			.thenReturn(true);
		when(groupRepository.save(any(Group.class)))
			.thenReturn(Group.builder()
				.id(id)
				.name(name)
				.build());
		
		Optional<Group> actualGroup = groupService.update(id, name);
		
		verify(groupRepository, times(1)).existsById(anyInt());
		verify(groupRepository, times(1)).save(any(Group.class));
		assertTrue(actualGroup.isPresent());
		assertEquals(name, actualGroup.get()
									  .getName());
		assertEquals(id, actualGroup.get()
									.getId());
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1, 2})
	void deleteById_groupWasDeleted_correctId(int id) {
		doNothing().when(groupRepository)
				   .deleteById(id);
		
		groupService.deleteById(id);
		
		verify(groupRepository, times(1)).deleteById(id);
	}
	
}
