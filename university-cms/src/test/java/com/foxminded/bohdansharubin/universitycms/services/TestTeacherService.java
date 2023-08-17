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
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foxminded.bohdansharubin.universitycms.convertor.ModelConvertor;
import com.foxminded.bohdansharubin.universitycms.dto.PersonDTO;
import com.foxminded.bohdansharubin.universitycms.models.Teacher;
import com.foxminded.bohdansharubin.universitycms.repositories.TeacherRepository;

@ExtendWith(MockitoExtension.class)
class TestTeacherService extends TestAbstractPersonService {
	
	@Mock
	private TeacherRepository teacherRepository;
	
	@Mock
	private ModelConvertor modelConvertor;
	
	@InjectMocks
	private TeacherService teacherService;
	
	private Teacher getCorrectTeacher() {
		Teacher correctTeacher = new Teacher();
		correctTeacher.setFirstName("Firstname");
		correctTeacher.setLastName("Lastname");
		correctTeacher.setUser(getCorrectUser());
		return correctTeacher;
	}
	
	private PersonDTO getCorrectDTO() {
		PersonDTO dto = convertor.convertToPersonDto(getCorrectTeacher());
		dto.setUserPassword("1234");
		return dto;
	}
	
	private PersonDTO getIncorrectDTO() {
		PersonDTO incorrectDTO = getCorrectDTO();
		incorrectDTO.setFirstName("");
		return incorrectDTO;
	}
	
	@Test
	void save_returnPersonDto_dataIsCorrect() {
		PersonDTO dto = getCorrectDTO();
		Teacher expectedTeacher = getCorrectTeacher();
		
		when(teacherRepository.save(any(Teacher.class)))
			.thenReturn(expectedTeacher);
		when(modelConvertor.convertToTeacher(any()))
			.thenReturn(convertor.convertToTeacher(dto));
		when(modelConvertor.convertToPersonDto(any()))
			.thenReturn(convertor.convertToPersonDto(expectedTeacher));
		
		
		PersonDTO actualTeacherDto = teacherService.save(getCorrectDTO());
		
		verify(teacherRepository, times(1)).save(any(Teacher.class));
		verify(modelConvertor, times(1)).convertToTeacher(any());
		verify(modelConvertor, times(1)).convertToPersonDto(any());
		assertFalse(actualTeacherDto.isNull());
		assertTrue(isPersonAndDtoEqual(expectedTeacher, actualTeacherDto));
	}
	
	@Test
	void save_returnNullPersonDTO_dataIsIncorrect() {
		when(modelConvertor.convertToTeacher(any()))
			.thenReturn(convertor.convertToTeacher(getIncorrectDTO()));
		
		assertTrue(teacherService.save(getIncorrectDTO()).isNull());
		verify(modelConvertor, times(1)).convertToTeacher(any());
	}
	
	@Test
	void findAll_returnEmptyList_teachersNotExist() {
		when(teacherRepository.findAll())
			.thenReturn(new ArrayList<>());
		when(modelConvertor.convertToPersonDtoList(any()))
			.thenReturn(convertor.convertToPersonDtoList(new ArrayList<>()));
		
		assertTrue(teacherService.findAll().isEmpty());
		verify(teacherRepository, times(1)).findAll();
		verify(modelConvertor, times(1)).convertToPersonDtoList(any());
	}
	
	@Test
	void findAll_returnTeachersList_teachersExist() {
		List<Teacher> expectedTeachers = new ArrayList<>();
		expectedTeachers.add(getCorrectTeacher());
		expectedTeachers.add(getCorrectTeacher());
		
		when(teacherRepository.findAll())
			.thenReturn(expectedTeachers);
		when(modelConvertor.convertToPersonDtoList(any()))
			.thenReturn(convertor.convertToPersonDtoList(expectedTeachers));
		
		List<PersonDTO> actualTeachersDto = teacherService.findAll();
		
		verify(teacherRepository, times(1)).findAll();
		verify(modelConvertor, times(1)).convertToPersonDtoList(any());
		assertFalse(teacherService.findAll().isEmpty());
		assertEquals(expectedTeachers.size(), actualTeachersDto.size());
		IntStream.range(0, expectedTeachers.size())
			.forEach(i -> 
				assertTrue(isPersonAndDtoEqual(expectedTeachers.get(i), actualTeachersDto.get(i))));
	}
	
	@Test
	void update_returnNullPersonDTO_teacherWithIdNotExist() {
		when(teacherRepository.existsById(anyInt()))
			.thenReturn(false);
		assertTrue(teacherService.update(getCorrectDTO()).isNull());
	}
	
	@Test
	void update_returnNullPersonDTO_incorrectData() {
		when(teacherRepository.existsById(anyInt()))
			.thenReturn(true);
		when(modelConvertor.convertToTeacher(any()))
			.thenReturn(convertor.convertToTeacher(getIncorrectDTO()));
		
		assertTrue(teacherService.update(getIncorrectDTO()).isNull());
		verify(teacherRepository, times(1)).existsById(anyInt());
		verify(modelConvertor, times(1)).convertToTeacher(any());
	}
	
	@Test
	void update_returnPersonDto_dataForUpdatingIsCorrect() {
		Teacher expectedTeacher = getCorrectTeacher();
		
		when(teacherRepository.existsById(anyInt()))
			.thenReturn(true);
		when(teacherRepository.save(any(Teacher.class)))
			.thenReturn(expectedTeacher);
		when(modelConvertor.convertToTeacher(any()))
			.thenReturn(expectedTeacher);
		when(modelConvertor.convertToPersonDto(any()))
			.thenReturn(convertor.convertToPersonDto(expectedTeacher));
		
		PersonDTO actualTeacherDto = teacherService.update(getCorrectDTO());
		
		verify(teacherRepository, times(1)).existsById(anyInt());
		verify(teacherRepository, times(1)).save(any(Teacher.class));
		verify(modelConvertor, times(1)).convertToTeacher(any());
		verify(modelConvertor, times(1)).convertToPersonDto(any());
		assertFalse(actualTeacherDto.isNull());
		assertTrue(isPersonAndDtoEqual(expectedTeacher, actualTeacherDto));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1, 2})
	void deleteBy_studentWasDeleted_correctId(int id) {
		doNothing().when(teacherRepository)
				   .deleteById(id);
		teacherService.deleteById(id);
		verify(teacherRepository, times(1)).deleteById(id);
	}
	
	@Test
	void findById_returnTeacherDto_teacherWithIdExists() {
		when(teacherRepository.getReferenceById(anyInt()))
			.thenReturn(getCorrectTeacher());
		when(modelConvertor.convertToPersonDto(any()))
			.thenReturn(convertor.convertToPersonDto(getCorrectTeacher()));
		
		assertTrue(isPersonAndDtoEqual(getCorrectTeacher(), teacherService.findById(anyInt())));
		verify(teacherRepository, times(1)).getReferenceById(anyInt());
		verify(modelConvertor, times(1)).convertToPersonDto(any());
		
	}
	
}
