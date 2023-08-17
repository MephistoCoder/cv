package com.foxminded.bohdansharubin.schoolspringbootapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.foxminded.bohdansharubin.schoolspringbootapp.TestConfig;
import com.foxminded.bohdansharubin.schoolspringbootapp.dao.StudentDao;
import com.foxminded.bohdansharubin.schoolspringbootapp.model.Course;
import com.foxminded.bohdansharubin.schoolspringbootapp.model.Group;
import com.foxminded.bohdansharubin.schoolspringbootapp.model.Student;
import com.foxminded.bohdansharubin.schoolspringbootapp.utils.UserInputUtils;

@SpringBootTest(classes = TestConfig.class)
class TestStudentService {
	private MockedStatic<UserInputUtils> mockedUserInput;
	
	@Mock
	private StudentDao studentDao;

	@Mock
	private GroupService groupService;
	
	@Mock
	private CourseService courseService;
	
	@InjectMocks
	private StudentService studentService;
	
	@BeforeEach
	void mockUserInput() {
		mockedUserInput = mockStatic(UserInputUtils.class);
	}
	
	@AfterEach
	void closeUserInput() {
		mockedUserInput.close();
	}
	
	@Test
	void getAllStudents_returnEmptyList_studentsNotExistsInTable() {
		when(studentDao.findAll())
			.thenReturn(new ArrayList<>());
		assertTrue(studentService.getAllStudents().isEmpty());
	}
	
	@Test
	void getAllStudents_returnStudentsList_studentsExistsInTable() {
		List<Student> expectedStudents = new ArrayList<>();
		expectedStudents.add(Mockito.mock(Student.class));
		expectedStudents.add(Mockito.mock(Student.class));
		expectedStudents.add(Mockito.mock(Student.class));
		when(studentDao.findAll())
			.thenReturn(expectedStudents);
		assertEquals(expectedStudents, studentService.getAllStudents());
	}
	
	@Test
	void getStudentsByCourse_returnStudentsList_studentsRelatedToCourseExistsInTable() {
		Course course = new Course("Test", "test");
		List<Student> expectedStudents = new ArrayList<>();
		Student student = new Student("Test", "Test");
		student.addStudentToCourse(course);
		expectedStudents.add(student);
		student = new Student("test", "test");
		student.addStudentToCourse(course);
		expectedStudents.add(student);
		course.setStudents(expectedStudents);
		assertEquals(expectedStudents, studentService.getStudentsByCourse(course));
	}
	
	@Test
	void getStudentsByCourse_returnEmptyList_studentsRelatedToCourseNotExistsInTable() {
		Course course = new Course("Test", "test");
		List<Student> actualStudents = studentService.getStudentsByCourse(course);
		assertTrue(actualStudents.isEmpty());
	}
	
	@Test
	void getStudentById_returnNull_studentNotExistsInTable() {
		mockedUserInput.when(UserInputUtils::getIntegerInputFromUser)
					   .thenReturn(1);
		when(studentDao.getReferenceById(1))
			.thenReturn(null);
		assertNull(studentService.getStudentById());
	}
	
	@Test
	void getStudentById_returnStudentWithSuchId_studentWithSuchIdExistsInTable() {
		mockedUserInput.when(UserInputUtils::getIntegerInputFromUser)
		   			   .thenReturn(1);
		Student expectedStudent = Mockito.mock(Student.class);
		when(studentDao.getReferenceById(1))
			.thenReturn(expectedStudent);
		assertEquals(expectedStudent, studentService.getStudentById());
	}
	
	@Test
	void deleteStudent_studentWasNotDeletedFromTable_studentNotExistsInTable() {
		Student student = new Student("test", "test");
		student.setId(1);
		mockedUserInput.when(UserInputUtils::getIntegerInputFromUser)
					   .thenReturn(1);
		when(studentDao.getReferenceById(1))
			.thenReturn(null);
		studentService.deleteStudent();
		Mockito.verify(studentDao, Mockito.times(0))
			   .deleteById(student.getId());
	}
	
	@Test
	void deleteStudent_studentWasDeletedFromTable_studentExistsInTable() {
		Student student = new Student("test", "test");
		student.setId(1);
		mockedUserInput.when(UserInputUtils::getIntegerInputFromUser)
					   .thenReturn(3);
		when(studentDao.getReferenceById(3))
			.thenReturn(student);
		doNothing().when(studentDao)
			   	   .deleteById(student.getId());
		studentService.deleteStudent();
		Mockito.verify(studentDao, Mockito.times(1))
			   .deleteById(student.getId());
	}

	@Test
	void addStudentToGroup_studentAssignedToGroup_groupAndStudentExists() {
		Student student = new Student("Test", "Test");
		student.setId(1);
		Group group = new Group("TE-22");
		group.setId(1);
		List<Group> groups = new ArrayList<>();
		groups.add(group);
		mockedUserInput.when(UserInputUtils::getIntegerInputFromUser)
		   			   .thenReturn(1);
		when(studentDao.getReferenceById(1))
			.thenReturn(student);
		when(groupService.getAllGroups())
			.thenReturn(groups);
		when(groupService.getById(1))
			.thenReturn(group);
		studentService.addStudentToGroup();
		assertEquals(groups.get(0), student.getGroup());
	}
	
	@Test
	void addStudentToCourse_studentAssignedToCourse_courseAndStudentExists() {
		Student student = new Student("Test", "Test");
		student.setId(1);
		Course course = new Course("Math", "test");
		course.setId(1);
		List<Course> courses = new ArrayList<>();
		courses.add(course);
		mockedUserInput.when(UserInputUtils::getIntegerInputFromUser)
			.thenReturn(1);
		when(studentDao.getReferenceById(1))
			.thenReturn(student);
		when(courseService.getAllCourses())
			.thenReturn(courses);
		when(courseService.findCourseById())
			.thenReturn(course);
		studentService.addStudentToCourse();
		assertEquals(course, student.getCourses().get(0));
	}
	
	@Test
	void deleteStudentFromCourse_studentWasDeletedFromCourse_courseAndStudentExists() {
		Student student = new Student("Test", "Test");
		student.setId(1);
		Course course = new Course("Math", "test");
		course.setId(1);
		student.addStudentToCourse(course);
		List<Course> courses = new ArrayList<>();
		courses.add(course);
		mockedUserInput.when(UserInputUtils::getIntegerInputFromUser)
			.thenReturn(1);
		when(studentDao.getReferenceById(1))
			.thenReturn(student);
		when(courseService.findCourseById())
			.thenReturn(course);
		studentService.deleteStudentFromCourse();
		assertTrue(student.getCourses().isEmpty());
	}
	

}
