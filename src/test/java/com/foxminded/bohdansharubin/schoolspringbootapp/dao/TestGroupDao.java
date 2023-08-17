package com.foxminded.bohdansharubin.schoolspringbootapp.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import com.foxminded.bohdansharubin.schoolspringbootapp.model.Group;

@DataJpaTest
@EnableAutoConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {GroupDao.class}) 
@EnableJpaRepositories(basePackages ="com.foxminded.bohdansharubin.schoolspringbootapp.dao")
@EntityScan(basePackages = "com.foxminded.bohdansharubin.schoolspringbootapp.model")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestGroupDao {
	
	@Autowired
	private GroupDao groupDao;
	
	@Sql("/scripts/groups/drop_groups_data.sql")
	@Test
	@Order(1)
	void getAll_returnEmptyList_tableIsEmpty() {
		assertEquals(0, groupDao.findAll()
	 						    .size());		
	}
	
	@Test
	@Order(2)
	void add_addedGroupExistsInTable_groupNotExistsInTable() {
		Group expectedGroup = new Group("GG-22");
		groupDao.save(expectedGroup);
		Group actualGroup = groupDao.getReferenceById(expectedGroup.getId());
		assertNotNull(actualGroup);
		assertEquals(expectedGroup, actualGroup);
	}
	
	@Sql("/scripts/groups/insert_groups.sql")
	@Test
	@Order(3)
	void getAll_returnListWithGroups_tableHasEntries() {
		List<Group> actualList = groupDao.findAll();
		List<Group> expectedList = new ArrayList<>();
		expectedList.add(new Group("GF-12"));
		expectedList.add(new Group("HD-36"));
		IntStream.range(0, expectedList.size())
				 .forEach(i -> expectedList.get(i).setId(i + 1));
		assertEquals(expectedList.size(), actualList.size());
		assertEquals(expectedList, actualList);
	}
	
	@ParameterizedTest
	@CsvSource({"GF-12", "HD-36"})
	@Order(4)
	void getById_returnPresentOptionalGroup_entryWithSuchIdExistsInTable(String groupName) {
		Group expectedGroup = new Group(groupName); 
		groupDao.save(expectedGroup);
		Group actualGroupOptional = groupDao.getReferenceById(expectedGroup.getId());
		assertNotNull(actualGroupOptional);
		assertEquals(expectedGroup, actualGroupOptional);
	}
	
	@Test
	@Order(5)
	void deleteById_deletedGroupNotExistsInTable_entryWithSuchIdExistsInTable() {
		Group expectedGroup = new Group("GG-22");
		groupDao.save(expectedGroup);
		Group actualGroup = groupDao.getReferenceById(expectedGroup.getId());
		assertNotNull(actualGroup);
		assertEquals(expectedGroup, actualGroup);
		groupDao.deleteById(actualGroup.getId());
		assertThrows(JpaObjectRetrievalFailureException.class, () -> groupDao.getReferenceById(actualGroup.getId()));	}
	
}