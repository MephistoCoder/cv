package com.foxminded.bohdansharubin.universitycms.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foxminded.bohdansharubin.universitycms.models.Audience;
import com.foxminded.bohdansharubin.universitycms.repositories.AudienceRepository;

@ExtendWith(MockitoExtension.class)
class TestAudienceService {
	
	@Mock
	AudienceRepository audienceRepository;
	
	@InjectMocks
	AudienceService audienceService;
	
	@ParameterizedTest
	@ValueSource(ints = {1, 2, 12})
	void findById_returnOptionalAudience_audienceWithIdExists(int id) {
		when(audienceRepository.getReferenceById(id))
			.thenReturn(Audience.builder()
								.id(id)
								.name("TE-1")
								.build());
		Optional<Audience> actualAudience = audienceService.findById(id);
		verify(audienceRepository, times(1)).getReferenceById(id);
		assertTrue(actualAudience.isPresent());
		assertEquals(id, actualAudience.get()
									   .getId());	
	}
	
	@Test
	void findAll_returnEmptyList_audiencesNotExist() {
		when(audienceRepository.findAll())
			.thenReturn(new ArrayList<>());
		assertTrue(audienceService.findAll().isEmpty());
		verify(audienceRepository, times(1)).findAll();	
	}
	
	@Test
	void findAll_returnAudiencesList_audiencesExist() {
		List<Audience> expectedAudiences = new ArrayList<>();
		expectedAudiences.add(new Audience(1, "AA-123"));
		expectedAudiences.add(new Audience(2, "FFA-123"));
		
		when(audienceRepository.findAll())
			.thenReturn(expectedAudiences);
		
		List<Audience> actualAudiences = audienceService.findAll();
		
		assertFalse(actualAudiences.isEmpty());
		verify(audienceRepository, times(1)).findAll();
		assertEquals(actualAudiences, expectedAudiences);
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1, 2, 12})
	void findById_returnOptionalEmpty_audienceWithIdNotExists(int id) {
		when(audienceRepository.getReferenceById(id))
			.thenReturn(null);
		
		Optional<Audience> actualAudience = audienceService.findById(id);
		
		verify(audienceRepository, times(1)).getReferenceById(id);
		assertFalse(actualAudience.isPresent());	
	}
	
	@ParameterizedTest
	@NullSource
	void findByName_returnOptionalEmpty_nameIsNull(String name) {
		assertFalse(audienceService.findByName(name)
								   .isPresent());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"AA-12", "AA-121", "GW-123", "GE-1"})
	void findByName_returnOptionalAudience_nameIsCorrect(String name) {
		when(audienceRepository.findByName(name))
			.thenReturn(Optional.of(Audience.builder()
											.id(1)
											.name(name)
											.build()));
		
		Optional<Audience> audienceOptional = audienceService.findByName(name);
		
		verify(audienceRepository, times(1)).findByName(name);
		assertTrue(audienceOptional.isPresent());
		assertEquals(name, audienceOptional.get()
										   .getName());
	}
	
}
