package com.foxminded.bohdansharubin.schoolspringbootapp.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

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

import com.foxminded.bohdansharubin.schoolspringbootapp.model.Student;

@DataJpaTest
@EnableAutoConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) 
@EnableJpaRepositories(basePackages ="com.foxminded.bohdansharubin.schoolspringbootapp.dao")
@EntityScan(basePackages = "com.foxminded.bohdansharubin.schoolspringbootapp.model")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestStudentDao {

	@Autowired
	private StudentDao studentDao;
	
	@Sql("/scripts/drop_data.sql")
	@Test
	@Order(1)
	void getAll_returnEmptyList_tableHasNoEntries() {
		assertEquals(0, studentDao.findAll()
								  .size());
	}
	
	@ParameterizedTest
	@CsvSource({"Bohdan, Gold", "John, Snow"})
	@Order(2)
	void add_studentExistsInTable_entryNotExistsInTable(String firstName,
														String lastName) {
		Student expectedStudent = new Student(firstName, lastName);
		studentDao.save(expectedStudent);
		Student actualStudent = studentDao.getReferenceById(expectedStudent.getId());
		assertNotNull(actualStudent);
		assertEquals(expectedStudent, actualStudent);
	}
	@Sql({"/scripts/students/insert_students.sql"})
	@Test
	@Order(3)
	void getAll_returnListWithStudents_tableHasEntries() {
		List<Student> actualStudents = studentDao.findAll();
		List<Student> expectedStudents = new ArrayList<>();
		Student student = new Student("Bohdan", "Gold");
		student.setId(1);
		expectedStudents.add(student);
		student = new Student("John", "Snow");
		student.setId(2);
		expectedStudents.add(student);
		assertEquals(expectedStudents.size(), actualStudents.size());
		assertEquals(expectedStudents, actualStudents);
	}
	
	@ParameterizedTest
	@CsvSource({"Bohdan, Gold", "John, Snow"})
	@Order(4)
	void getById_returnStudent_entryWithSuchIdExistsInTable(String firstName,
																		   String lastName) {
		Student expectedStudent = new Student(firstName, lastName);
		studentDao.save(expectedStudent);
		Student actualStudentOptional = studentDao.getReferenceById(expectedStudent.getId());
		assertNotNull(actualStudentOptional);
		assertEquals(expectedStudent, actualStudentOptional);
	}
	
	@ParameterizedTest
	@CsvSource({"Bohdan, Gold", "John, Snow"})
	@Order(5)
	void deleteById_deletedEntryNotExistsInTable_entryWithSuchIdExistsInTable(String firstName,
																			  String lastName) {
		Student expectedStudent = new Student(firstName, lastName);
		studentDao.save(expectedStudent);
		Student actualStudent = studentDao.getReferenceById(expectedStudent.getId());
		assertNotNull(actualStudent);
		studentDao.deleteById(actualStudent.getId());
		assertThrows(JpaObjectRetrievalFailureException.class, () -> studentDao.getReferenceById(actualStudent.getId())); 
	}
	 
}
