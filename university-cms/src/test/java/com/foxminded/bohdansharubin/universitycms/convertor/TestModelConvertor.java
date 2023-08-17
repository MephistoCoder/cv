package com.foxminded.bohdansharubin.universitycms.convertor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.foxminded.bohdansharubin.universitycms.TestConfig;
import com.foxminded.bohdansharubin.universitycms.dto.CourseDTO;
import com.foxminded.bohdansharubin.universitycms.dto.PersonDTO;
import com.foxminded.bohdansharubin.universitycms.dto.UserDTO;
import com.foxminded.bohdansharubin.universitycms.models.Admin;
import com.foxminded.bohdansharubin.universitycms.models.Course;
import com.foxminded.bohdansharubin.universitycms.models.Person;
import com.foxminded.bohdansharubin.universitycms.models.Roles;
import com.foxminded.bohdansharubin.universitycms.models.Student;
import com.foxminded.bohdansharubin.universitycms.models.Teacher;
import com.foxminded.bohdansharubin.universitycms.models.User;

@SpringBootTest(classes = ModelConvertor.class)
class TestModelConvertor {
	
	@Autowired
	private ModelConvertor modelConvertor;
	
	private boolean isPersonAndDtoEqual(Person person, PersonDTO dto) {
		return person.getId() == dto.getId() &&
			person.getFirstName() == dto.getFirstName() &&
			person.getLastName() == dto.getLastName() &&
			person.getUser().getId() == dto.getUserId() &&
			person.getUser().getUsername() == dto.getUserUsername() &&
			person.getUser().getRole() == dto.getUserRole();
	}
	
	private boolean isCourseAndDtoEqual(Course course, CourseDTO dto) {
		return course.getId() == dto.getId() &&
				course.getName() == dto.getName() &&
				course.getDescription() == dto.getDescription();
	}
	
	private Student getCorrectStudent(String dataModifier) {
		Student student = new Student();
		student.setId(1);
		student.setFirstName("FirstName" + dataModifier);
		student.setLastName("LastName" + dataModifier);
		student.setUser(getCorrectUser(dataModifier));
		return student;
	}
	
	private Teacher getCorrectTeacher(String dataModifier) {
		Teacher teacher = new Teacher();
		teacher.setId(1);
		teacher.setFirstName("FirstName" + dataModifier);
		teacher.setLastName("LastName" + dataModifier);
		teacher.setUser(getCorrectUser(dataModifier));
		return teacher;
	}
	
	private boolean isUserAndUserDtoEqual(User user, UserDTO dto) {
		return user.getId() == dto.getId() &&
				user.getUsername() == dto.getUsername() &&
				user.getRole() == dto.getRole();
	}
	
	private User getCorrectUser(String dataModifier) {
		return User.builder()
			.id(1)
			.username("Username1234" + dataModifier)
			.password("pass1234!$" + dataModifier)
			.role(Roles.ROLE_STUDENT)
			.build();
	}
	
	private List<Student> getCorrectStudentList(int listLength) {
		List<Student> students = new ArrayList<>(listLength);
		IntStream.range(0, listLength)
			.forEach(i ->  students.add(getCorrectStudent(String.valueOf(i))));
		return students;
	}
	
	private PersonDTO getCorrectPersonDto(String dataModifier) {
		PersonDTO personDTO = new PersonDTO();
		personDTO.setId(1);
		personDTO.setFirstName("Firstname" + dataModifier);
		personDTO.setLastName("Lastname" + dataModifier);
		personDTO.setUserId(1);
		personDTO.setUserPassword("Password1234" + dataModifier);;
		personDTO.setUserUsername("Username1234" + dataModifier);
		personDTO.setUserRole(Roles.ROLE_STUDENT);;
		return personDTO;
	}
	
	private UserDTO getCorrectUserDtoWithPassword(String password) {
		UserDTO userDto = new UserDTO();
		userDto.setId(1);
		userDto.setUsername("Username1234");
		userDto.setPassword(password);
		userDto.setRole(Roles.ROLE_ADMIN);
		return userDto;
	}
	
	private List<User> getCorrectUserList(int listLength) {
		List<User> users = new ArrayList<>(listLength);
		IntStream.range(0, listLength)
			.forEach(i -> users.add(getCorrectUser(String.valueOf(i))));
		return users;
	}
	
	@Test
	void convertToPersonDto_returnDtoWithoutPassword_personIsCorrect() {
		Person person = getCorrectStudent("");
		PersonDTO dto = modelConvertor.convertToPersonDto(person);
		
		assertTrue(isPersonAndDtoEqual(person, dto));
		assertEquals("", dto.getUserPassword());
	}
	
	@Test
	void convertToPersonDtoList_returnListOfDtoWithoutPassword_listOfPersonsIsCorrect() {
		List<Student> students = getCorrectStudentList(5);
		List<PersonDTO> dtoList = modelConvertor.convertToPersonDtoList(students);
		
		assertEquals(students.size(), dtoList.size());
		IntStream.range(0, students.size())
			.forEach(i -> {
				assertTrue(isPersonAndDtoEqual(students.get(i), dtoList.get(i)));
				assertEquals("", dtoList.get(i).getUserPassword());				
			});
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"1", "a", "@", "Test1%"})
	void convertToUserDto_returnUserDtoWithoutPassword_userIsCorrect(String userDataModifier) {
		User expectedUser = getCorrectUser(userDataModifier);
		UserDTO actualUserDto = modelConvertor.convertToUserDto(expectedUser);

		assertTrue(isUserAndUserDtoEqual(expectedUser, actualUserDto));
		assertTrue(actualUserDto.getPassword().isEmpty());
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1, 3, 10})
	void convertToUserDtoList_returnUserDtoListWithoutPasswords_userListIsCorrect(int usersListLength) {
		List<User> expectedUsers = getCorrectUserList(usersListLength);
		List<UserDTO> actualUsersDto = modelConvertor.convertToUserDtoList(expectedUsers);
		
		assertEquals(expectedUsers.size(), actualUsersDto.size());
		IntStream.range(0, expectedUsers.size())
			.forEach(userId -> {
				assertTrue(isUserAndUserDtoEqual(expectedUsers.get(userId), actualUsersDto.get(userId)));
				assertTrue(actualUsersDto.get(userId).getPassword().isEmpty());
			});
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"", "1234", "pass1234!$"})
	void convertToUser_returnUser_correctDto(String password) {
		UserDTO dto = getCorrectUserDtoWithPassword(password);
		User actualUser = modelConvertor.convertToUser(dto);

		assertTrue(isUserAndUserDtoEqual(actualUser, dto));
	}
	
	
	@ParameterizedTest
	@ValueSource(strings = {"a", "1", "abc123!@"})
	void convertToStudent_returnStudent_personDtoCorrect(String dataModifier) {
		PersonDTO dto = getCorrectPersonDto(dataModifier);
		Student student = modelConvertor.convertToStudent(dto);

		assertTrue(isPersonAndDtoEqual(student, dto));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"a", "1", "abc123!@"})
	void convertToTeacher_returnTeacher_personDtoCorrect(String dataModifier) {
		PersonDTO dto = getCorrectPersonDto(dataModifier);
		Teacher teacher = modelConvertor.convertToTeacher(dto);
		
		assertTrue(isPersonAndDtoEqual(teacher, dto));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"a", "1", "abc123!@"})
	void convertToAdmin_returnAdmin_dtoCorrect(String dataModifier) {
		PersonDTO dto = getCorrectPersonDto(dataModifier);
		Admin admin = modelConvertor.convertToAdmin(dto);

		assertTrue(isPersonAndDtoEqual(admin, dto));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"a", "1", "abc123!@"})
	void courseToDto_returnCourseDTO_courseCorrect(String dataModifier) {
		Course course = Course.builder()
			.id(1)
			.name("Test course")
			.description("Test description")
			.build();
		course.addStudent(getCorrectStudent(dataModifier));
		course.addTeacher(getCorrectTeacher(dataModifier));
		
		CourseDTO dto = modelConvertor.courseToDto(course);
		
		assertTrue(isCourseAndDtoEqual(course, dto));
		assertEquals(dto.getTeachers().size(), course.getTeachers().size());
		IntStream.range(0, dto.getTeachers().size())
			.forEach(i -> assertEquals(dto.getTeachers().get(i), course.getTeachers()
				.get(i)
				.getId())
			);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"a", "1", "abc123!@"})
	void dtoToCourse_returnCourse_courseDtoCorrect(String dataModifier) {
		
		CourseDTO dto = new CourseDTO();
		dto.setId(1);
		dto.setName("Test");
		dto.setDescription("Test description");
		
		List<Integer> teachersIds = new ArrayList<>();
		teachersIds.add(1);
		dto.setTeachers(teachersIds);
		Course course = modelConvertor.dtoToCourse(dto);
		System.out.println(course);
		
		assertTrue(isCourseAndDtoEqual(course, dto));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"a", "1", "abc123!@"})
	void convertToCourseDtoList_returnCourseDtoList_courseListCorrect(String dataModifier) {
		List<Course> courses = new ArrayList<>();
		courses.add(Course.builder()
			.id(1)
			.name("Test" + dataModifier)
			.description("Test")
			.build());
		courses.add(Course.builder()
			.id(1)
			.name("Test" + dataModifier)
			.description("Test")
			.build());
		List<CourseDTO> dtos = modelConvertor.convertToCourseDtoList(courses);
		IntStream.range(0, dtos.size())
			.forEach(i -> assertTrue(isCourseAndDtoEqual(courses.get(i), dtos.get(i))));
	}
	
}
