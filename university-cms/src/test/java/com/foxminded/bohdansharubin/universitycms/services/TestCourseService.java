package com.foxminded.bohdansharubin.universitycms.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foxminded.bohdansharubin.universitycms.convertor.ModelConvertor;
import com.foxminded.bohdansharubin.universitycms.dto.CourseDTO;
import com.foxminded.bohdansharubin.universitycms.exceptions.CourseNotFoundException;
import com.foxminded.bohdansharubin.universitycms.models.Course;
import com.foxminded.bohdansharubin.universitycms.models.Teacher;
import com.foxminded.bohdansharubin.universitycms.repositories.CourseRepository;

@ExtendWith(MockitoExtension.class)
class TestCourseService {
	
	@Spy
	private ModelConvertor modelConvertor;
	
	@Mock
	private CourseRepository courseRepository;

	@InjectMocks
	private CourseService courseService;
	
	private boolean isCourseAndDtoEqual(Course course, CourseDTO dto) {
		return course.getId() == dto.getId() &&
			course.getName() == dto.getName() &&
			course.getDescription() == dto.getDescription() &&
			IsCourseTeachersAndDtoTeachersEqual(course.getTeachers(), dto.getTeachers());
	}
	
	private boolean IsCourseTeachersAndDtoTeachersEqual(List<Teacher> teachers, List<Integer> dtoTeachersId) {
		List<Integer> teachersIds = teachers.stream()
			.map(Teacher::getId)
			.collect(Collectors.toList());
		return teachersIds.equals(dtoTeachersId);
	}
	
	@ParameterizedTest
	@CsvSource({"Math, test", "Test, test description"})
	void save_returnOptionalCourse_correctNameAndDescription(String name, String description) {
		Course expectedCourse = Course.builder()
			.name(name)
			.description(description)
			.build();
		when(courseRepository.save(any(Course.class)))
			.thenReturn(expectedCourse);

		CourseDTO dto = new CourseDTO();
		dto.setName(name);
		dto.setDescription(description);
		
		CourseDTO actualCourse = courseService.save(dto);
		
		verify(courseRepository, times(1)).save(any(Course.class));
		verify(modelConvertor, times(1)).dtoToCourse(any());
		verify(modelConvertor, times(1)).courseToDto(any());
		
		assertNotNull(actualCourse);
		assertTrue(isCourseAndDtoEqual(expectedCourse, actualCourse));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1, 2, 12})
	void findById_returnOptionalCourse_courseWithIdExists(int id) {
		Course course = Course.builder()
				.id(id)
				.name("Test")
				.description("description")
				.build();
		when(courseRepository.findById(anyInt()))
			.thenReturn(Optional.of(course));
		
		CourseDTO outputDto = courseService.findById(id);
		
		verify(courseRepository, times(1)).findById(anyInt());
		verify(modelConvertor, times(1)).courseToDto(any());
		assertNotNull(outputDto);
		assertTrue(isCourseAndDtoEqual(course, outputDto));	
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1, 2, 12})
	void findById_throwCourseNotFoundException_courseWithIdNotExists(int id) {
		when(courseRepository.findById(any()))
			.thenReturn(Optional.empty());
		
		assertThrows(CourseNotFoundException.class, () -> courseService.findById(id));
		
		verify(courseRepository, times(1)).findById(any());
	}
	
	@Test
	void findAll_returnEmptyList_coursesNotExist() {
		when(courseRepository.findAllByOrderById())
			.thenReturn(new ArrayList<>());
		
		assertTrue(courseService.findAll().isEmpty());	
		verify(courseRepository, times(1)).findAllByOrderById();
	}
	
	@Test
	void findAll_returnCoursesList_coursesExist() {
		List<Course> expectedCourses = new ArrayList<>();
		expectedCourses.add(Course.builder()
								  .id(1)
								  .name("Math")
								  .build());
		expectedCourses.add(Course.builder()
								  .id(2)
								  .name("IT")
								  .build());
		when(courseRepository.findAllByOrderById())
			.thenReturn(expectedCourses);
		
		List<CourseDTO> actualCourses = courseService.findAll();
		
		verify(courseRepository, times(1)).findAllByOrderById();
		verify(modelConvertor, times(1)).convertToCourseDtoList(any());
		
		assertFalse(actualCourses.isEmpty());
		assertEquals(expectedCourses.size(), actualCourses.size());
		IntStream.range(0, expectedCourses.size())
			.forEach(courseIndex -> assertTrue(isCourseAndDtoEqual(expectedCourses.get(courseIndex),
				actualCourses.get(courseIndex)))
			);
	}
	
	@Test
	void update_throwCourseNotFoundException_courseWithIdNotFound() {
		when(courseRepository.existsById(anyInt()))
			.thenReturn(false);
		
		assertThrows(CourseNotFoundException.class, () -> courseService.update(new CourseDTO()));
		
		verify(courseRepository, times(1)).existsById(anyInt());
	}
	
	@ParameterizedTest
	@CsvSource({"1, TEST, TEST DESCRIPTION", "2, Math, high math course"})
	void update_returnOptionalCourse_correctData(int id, String name, String description) {
		CourseDTO dto = new CourseDTO();
		dto.setId(id);
		dto.setName(name);
		dto.setDescription(description);
		
		Course expectedCourse = Course.builder()
			.id(id)
			.name(name)
			.description(description)
			.build();
		
		when(courseRepository.existsById(id))
			.thenReturn(true);
		when(courseRepository.save(any(Course.class)))
			.thenReturn(expectedCourse);
		
		CourseDTO outputDto = courseService.update(dto);
		
		verify(courseRepository, times(1)).existsById(anyInt());
		verify(courseRepository, times(1)).save(any());
		verify(modelConvertor, times(1)).courseToDto(any());
		verify(modelConvertor, times(1)).dtoToCourse(any());
		
		assertNotNull(outputDto);
		assertTrue(isCourseAndDtoEqual(expectedCourse, outputDto));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1, 2})
	void deleteById_returnOptionalCourse_correctData(int id) {
		doNothing().when(courseRepository)
				   .deleteById(id);
		
		courseService.deleteById(id);
		
		verify(courseRepository, times(1)).deleteById(id);
	}

}
