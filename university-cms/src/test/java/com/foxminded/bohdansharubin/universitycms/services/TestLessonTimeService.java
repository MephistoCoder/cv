package com.foxminded.bohdansharubin.universitycms.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foxminded.bohdansharubin.universitycms.models.LessonTime;
import com.foxminded.bohdansharubin.universitycms.repositories.LessonTimeRepository;

@ExtendWith(MockitoExtension.class)
class TestLessonTimeService {

	@Mock
	LessonTimeRepository lessonTimeRepository;
	
	@InjectMocks
	LessonTimeService lessonTimeService;
	
	@ParameterizedTest
	@ValueSource(ints = {1, 2, 12})
	void findById_returnOptionalLessonTime_lessonTimeWithIdExists(int id) {
		when(lessonTimeRepository.getReferenceById(id))
			.thenReturn(LessonTime.builder()
								.id(id)
								.startTime(LocalTime.of(12, 0))
								.endTime(LocalTime.of(13, 0))
								.build());
		Optional<LessonTime> actualLessonTime = lessonTimeService.findById(id);
		
		verify(lessonTimeRepository, times(1)).getReferenceById(id);
		assertTrue(actualLessonTime.isPresent());
		assertEquals(id, actualLessonTime.get()
									   .getId());	
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1, 2, 12})
	void findById_returnOptionalEmpty_lessonTimeWithIdNotExists(int id) {
		when(lessonTimeRepository.getReferenceById(id))
			.thenReturn(null);
		
		Optional<LessonTime> actualLessonTime = lessonTimeService.findById(id);
		
		verify(lessonTimeRepository, times(1)).getReferenceById(id);
		assertFalse(actualLessonTime.isPresent());	
	}
	
	@Test
	void findAll_returnEmptyList_lessonsTimesNotExist() {
		when(lessonTimeRepository.findAll())
			.thenReturn(new ArrayList<>());
		
		assertTrue(lessonTimeService.findAll().isEmpty());	
		verify(lessonTimeRepository, times(1)).findAll();
	}
	
	@Test
	void findAll_returnLessonsTimesList_lessonsTimesExist() {
		List<LessonTime> expectedLessonsTimes = new ArrayList<>();
		expectedLessonsTimes.add(new LessonTime(1, LocalTime.now(), LocalTime.now().plusMinutes(1)));
		expectedLessonsTimes.add(new LessonTime(1, LocalTime.now(), LocalTime.now().plusMinutes(1)));
		
		when(lessonTimeRepository.findAll())
			.thenReturn(expectedLessonsTimes);
		
		List<LessonTime> actualLessonsTimes = lessonTimeService.findAll();
		
		verify(lessonTimeRepository, times(1)).findAll();
		assertFalse(actualLessonsTimes.isEmpty());
		assertEquals(actualLessonsTimes, expectedLessonsTimes);
	}
	
}
