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
import com.foxminded.bohdansharubin.universitycms.models.Student;
import com.foxminded.bohdansharubin.universitycms.repositories.StudentRepository;

@ExtendWith(MockitoExtension.class)
class TestStudentService extends TestAbstractPersonService {
	
	@Mock
	private StudentRepository studentRepository;
	
	@Mock
	private ModelConvertor modelConvertor;
	
	@InjectMocks
	private StudentService studentService;
	
	private Student getCorrectStudent() {
		Student correctStudent = new Student();
		correctStudent.setId(1);
		correctStudent.setFirstName("Firstname");
		correctStudent.setLastName("Lastname");
		correctStudent.setUser(getCorrectUser());
		return correctStudent;
	}
	
	private Student getIncorrectStudent() {
		Student incorrectStudent = getCorrectStudent();
		incorrectStudent.setId(-1);
		incorrectStudent.setFirstName("");
		incorrectStudent.setLastName("");
		incorrectStudent.setUser(getCorrectUser());
		return incorrectStudent;
	}
	
	private PersonDTO getCorrectDTO() {
		PersonDTO dto = convertor.convertToPersonDto(getCorrectStudent());
		dto.setUserPassword("1234");
		return dto;
	}
	
	private PersonDTO getIncorrectDTO() {
		PersonDTO incorrectDTO = getCorrectDTO();
		incorrectDTO.setFirstName("");
		return incorrectDTO;
	}
	
	@Test
	void save_returnPersonDto_dtoIsCorrect() {
		Student expectedStudent = getCorrectStudent();
		PersonDTO dto = getCorrectDTO();
		
		
		when(studentRepository.save(any(Student.class)))
			.thenReturn(expectedStudent);
		when(modelConvertor.convertToStudent(any()))
			.thenReturn(convertor.convertToStudent(dto));
		when(modelConvertor.convertToPersonDto(any()))
			.thenReturn(convertor.convertToPersonDto(expectedStudent));
		
		PersonDTO actualStudentDto = studentService.save(dto);
		
		verify(studentRepository, times(1)).save(any(Student.class));
		verify(modelConvertor, times(1)).convertToStudent(any());
		verify(modelConvertor, times(1)).convertToPersonDto(any());
		assertFalse(actualStudentDto.isNull());		
		assertTrue(isPersonAndDtoEqual(expectedStudent, actualStudentDto));
	}
	
	@Test
	void save_returnNullPersonDTO_dtoIsIncorrect() {
		when(modelConvertor.convertToStudent(any()))
			.thenReturn(getIncorrectStudent());
		assertTrue(studentService.save(getIncorrectDTO()).isNull());
		verify(modelConvertor, times(1)).convertToStudent(any());
	}
	
	@Test
	void findAll_returnEmptyList_studentsNotExist() {
		when(studentRepository.findAll())
			.thenReturn(new ArrayList<>());
		when(modelConvertor.convertToPersonDtoList(any()))
			.thenReturn(new ArrayList<>());
		
		assertTrue(studentService.findAll().isEmpty());
		verify(studentRepository, times(1)).findAll();
		verify(modelConvertor, times(1)).convertToPersonDtoList(any());
	}
	
	@Test
	void findAll_returnStudentsList_studentsExist() {
		List<Student> expectedStudents = new ArrayList<>();
		expectedStudents.add(getCorrectStudent());
		expectedStudents.add(getCorrectStudent());
		
		when(studentRepository.findAll())
			.thenReturn(expectedStudents);
		when(modelConvertor.convertToPersonDtoList(any()))
			.thenReturn(convertor.convertToPersonDtoList(expectedStudents));
		
		List<PersonDTO> actualStudentsDTO = studentService.findAll();
		
		verify(studentRepository, times(1)).findAll();
		verify(modelConvertor, times(1)).convertToPersonDtoList(any());
		assertFalse(actualStudentsDTO.isEmpty());
		assertEquals(expectedStudents.size(), actualStudentsDTO.size());
		IntStream.range(0, expectedStudents.size())
			.forEach(i -> assertTrue(
				isPersonAndDtoEqual(expectedStudents.get(i), actualStudentsDTO.get(i))));
	}
	
	@Test
	void update_returnNullPersonDTO_studentWithIdNotFound() {
		when(studentRepository.existsById(anyInt()))
			.thenReturn(false);
		assertTrue(studentService.update(getCorrectDTO()).isNull());
	}
	
	@Test
	void update_returnPersonDTO_correctData() {
		Student expectedStudent = getCorrectStudent();
		PersonDTO dto = getCorrectDTO();
		
		when(studentRepository.existsById(anyInt()))
			.thenReturn(true);
		when(studentRepository.save(any(Student.class)))
			.thenReturn(expectedStudent);
		when(modelConvertor.convertToStudent(any()))
			.thenReturn(convertor.convertToStudent(dto));
		when(modelConvertor.convertToPersonDto(any()))
			.thenReturn(convertor.convertToPersonDto(expectedStudent));
		
		PersonDTO actualStudentDto = studentService.update(dto);
		
		verify(studentRepository, times(1)).existsById(anyInt());
		verify(modelConvertor, times(1)).convertToStudent(any());
		verify(modelConvertor, times(1)).convertToPersonDto(any());
		assertFalse(actualStudentDto.isNull());
		assertTrue(isPersonAndDtoEqual(expectedStudent, actualStudentDto));
	}
	
	@Test
	void update_returnNullPersonDTO_incorrectData() {
		when(studentRepository.existsById(anyInt()))
			.thenReturn(true);		
		when(modelConvertor.convertToStudent(any()))
			.thenReturn(getIncorrectStudent());
		
		assertTrue(studentService.update(getIncorrectDTO()).isNull());
		verify(modelConvertor, times(1)).convertToStudent(any());
		verify(studentRepository, times(1)).existsById(anyInt());
	}
	
	@Test
	void findById_returnPersonDTO_studentWithIdExists() {
		when(studentRepository.getReferenceById(anyInt()))
			.thenReturn(getCorrectStudent());		
		when(modelConvertor.convertToPersonDto(any()))
		.thenReturn(getCorrectDTO());
		
		assertTrue(isPersonAndDtoEqual(getCorrectStudent(), studentService.findById(anyInt())));
		verify(studentRepository, times(1)).getReferenceById(anyInt());
		verify(modelConvertor, times(1)).convertToPersonDto(any());
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1, 2})
	void deleteById_returnOptionalStudent_correctId(int id) {
		doNothing().when(studentRepository)
				   .deleteById(id);
		studentService.deleteById(id);
		verify(studentRepository, times(1)).deleteById(id);
	}

}
