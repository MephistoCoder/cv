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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foxminded.bohdansharubin.universitycms.models.Audience;
import com.foxminded.bohdansharubin.universitycms.models.Course;
import com.foxminded.bohdansharubin.universitycms.models.Lesson;
import com.foxminded.bohdansharubin.universitycms.models.LessonTime;
import com.foxminded.bohdansharubin.universitycms.models.Roles;
import com.foxminded.bohdansharubin.universitycms.models.Teacher;
import com.foxminded.bohdansharubin.universitycms.models.User;
import com.foxminded.bohdansharubin.universitycms.repositories.LessonRepository;

@ExtendWith(MockitoExtension.class)
class TestLessonService {
	
	@Mock
	LessonRepository lessonRepository;
	
	@InjectMocks
	LessonService lessonService;
	
	private Lesson getCorrectLesson() {
		Lesson lesson = new Lesson();
		lesson.setAudience(Audience.builder()
								   .name("AA-12")
								   .build());
		lesson.setDate(LocalDate.of(2022, 12, 20));
		lesson.setLessonTime(LessonTime.builder()
									   .startTime(LocalTime.of(12, 0))
									   .endTime(LocalTime.of(13, 0))
									   .build());
		lesson.setCourse(Course.builder()
							   .name("Math")
							   .build());
		Teacher teacher = new Teacher();
		teacher.setFirstName("Teacher");
		teacher.setLastName("Teacher");
		teacher.setUser(User.builder()
			.id(1)
			.username("Username")
			.password("Password")
			.role(Roles.ROLE_ADMIN)
			.build());
		lesson.setTeacher(teacher);
		return lesson;
	}
	
	@Test
	void save_returnOptionalLesson_dataIsCorrect() {
		Lesson correctLesson = getCorrectLesson();
		
		when(lessonRepository.save(any(Lesson.class)))
			.thenReturn(correctLesson);
		
		Optional<Lesson> actualLesson = lessonService.save(correctLesson);
		
		verify(lessonRepository, times(1)).save(any(Lesson.class));
		assertTrue(actualLesson.isPresent());
		assertEquals(correctLesson, actualLesson.get());
	}
	
	@Test
	void save_returnOptionalEmpty_dataIsIncorrect() {
		Lesson incorrectLesson = getCorrectLesson();
		incorrectLesson.setDate(LocalDate.of(2023, 1, 29));
		Optional<Lesson> actualLesson = lessonService.save(incorrectLesson);
		
		assertFalse(actualLesson.isPresent());
	}
		
	@Test
	void update_returnOptionalLesson_lessonIsCorrect() {
		Lesson correctLesson = getCorrectLesson();
		
		when(lessonRepository.existsById(anyInt()))
			.thenReturn(true);
		when(lessonRepository.save(any(Lesson.class)))
			.thenReturn(correctLesson);
		Optional<Lesson> actualLesson = lessonService.update(1, correctLesson);
		
		verify(lessonRepository, times(1)).existsById(anyInt());
		assertTrue(actualLesson.isPresent());
		assertEquals(correctLesson, actualLesson.get());
	}
	
	@Test
	void update_returnOptionalEmpty_lessonIsIncorrect() {
		Lesson incorrectLesson = new Lesson();
		
		Optional<Lesson> actualLesson = lessonService.update(1, incorrectLesson);
		
		assertFalse(actualLesson.isPresent());
	}
	
	@Test
	void update_returnOptionalEmpty_lessonWithIdNotExist() {
		when(lessonRepository.existsById(anyInt()))
			.thenReturn(false);
		
		Optional<Lesson> actualLesson = lessonService.update(1, getCorrectLesson());
		
		assertFalse(actualLesson.isPresent());
	}
	
	@Test
	void findById_returnOptionalEmpty_lessonWithIdNotFound() {
		when(lessonRepository.getReferenceById(anyInt()))
			.thenReturn(null);
		
		Optional<Lesson> actualLesson = lessonService.findById(1);
		
		assertFalse(actualLesson.isPresent());
	}
	
	@Test
	void findById_returnOptionalLesson_lessonWithIdExists() {
		Lesson correctLesson = getCorrectLesson();
		when(lessonRepository.getReferenceById(anyInt()))
			.thenReturn(correctLesson);
		
		Optional<Lesson> actualLesson = lessonService.findById(1);
		
		verify(lessonRepository, times(1)).getReferenceById(anyInt());
		assertTrue(actualLesson.isPresent());
		assertEquals(correctLesson, actualLesson.get());
	}
	
	@Test
	void deleteById_lessonWasDeleted_lessonWithIdNotFound() {
		doNothing().when(lessonRepository)
				   .deleteById(anyInt());
		
		lessonService.deleteById(1);
		
		verify(lessonRepository, times(1)).deleteById(anyInt());
	}
	
}
