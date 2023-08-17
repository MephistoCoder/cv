package com.foxminded.bohdansharubin.universitycms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.bohdansharubin.universitycms.models.Audience;
import com.foxminded.bohdansharubin.universitycms.repositories.AudienceRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AudienceService {

	private AudienceRepository audienceRepository;
	
	@Autowired
	public AudienceService(AudienceRepository audienceRepository) {
		this.audienceRepository = audienceRepository;
	}
	
	public Optional<Audience> findById(int id) {
		log.debug("Id for finding audience = {}", id);
		return Optional.ofNullable(audienceRepository.getReferenceById(id));
	}
	
	public List<Audience> findAll() {
		log.debug("Finding all audiences");
		return audienceRepository.findAll();
	}
	
	public Optional<Audience> findByName(String name) {
		if(name == null) {
			log.warn("Can't find audience with name is null");
			return Optional.empty();
		}
		log.debug("Name for finding audience = {}", name);
		return audienceRepository.findByName(name);
	}
	
}
