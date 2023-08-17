package com.foxminded.bohdansharubin.universitycms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.bohdansharubin.universitycms.models.LessonTime;
import com.foxminded.bohdansharubin.universitycms.repositories.LessonTimeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LessonTimeService {

	private LessonTimeRepository lessonTimeRepository;
	
	@Autowired
	public LessonTimeService(LessonTimeRepository lessonTimeRepository) {
		this.lessonTimeRepository = lessonTimeRepository;
	}
	
	public Optional<LessonTime> findById(int id) {
		log.debug("Id for finding lesson time = {}", id);
		return Optional.ofNullable(lessonTimeRepository.getReferenceById(id));
	}
	
	public List<LessonTime> findAll() {
		log.debug("Finding all lessons times");
		return lessonTimeRepository.findAll();
	}
	
}
