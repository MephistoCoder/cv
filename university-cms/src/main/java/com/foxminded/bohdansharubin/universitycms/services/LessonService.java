package com.foxminded.bohdansharubin.universitycms.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.bohdansharubin.universitycms.models.Lesson;
import com.foxminded.bohdansharubin.universitycms.repositories.LessonRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LessonService {
	
	private LessonRepository lessonRepository;
	
	@Autowired
	public LessonService(LessonRepository lessonRepository) {
		this.lessonRepository = lessonRepository;
	}
		
	@Transactional
	public Optional<Lesson> save(Lesson lessonDTO) {
		Lesson lesson = Lesson.builder()
							  .date(lessonDTO.getDate())
							  .lessonTime(lessonDTO.getLessonTime())
							  .course(lessonDTO.getCourse())
							  .teacher(lessonDTO.getTeacher())
							  .audience(lessonDTO.getAudience())
							  .build();
		if(!lesson.isValid()) {
			log.warn("Can't create lesson with invalid data");
			return Optional.empty();
		} 
		log.info("Created lesson = {}", lesson);
		return Optional.of(lessonRepository.save(lesson));
	}
	
	public Optional<Lesson> findById(int id) {
		log.debug("Id for finding lesson = {}", id);
		return Optional.ofNullable(lessonRepository.getReferenceById(id));
	}
	
	@Transactional
	public Optional<Lesson> update(int id, Lesson lessonDTO) {
		if(!existsById(id)) {
			log.warn("Can't find lesson witn id = {}", id);
			return Optional.empty();
		}
		
		Lesson lessonForUpdating = new Lesson();
		lessonForUpdating.setId(id);
		lessonForUpdating.setDate(lessonDTO.getDate());
		lessonForUpdating.setLessonTime(lessonDTO.getLessonTime());
		lessonForUpdating.setCourse(lessonDTO.getCourse());
		lessonForUpdating.setTeacher(lessonDTO.getTeacher());
		lessonForUpdating.setAudience(lessonDTO.getAudience());
		
		if(!lessonForUpdating.isValid()) {
			log.warn("Lesson for updating is invalid");
			return Optional.empty();
		}
		return Optional.of(lessonRepository.save(lessonForUpdating));
	}
	
	@Transactional
	public void deleteById(int id) {
		log.debug("Id for deleting = {}", id);
		lessonRepository.deleteById(id);
	}
	
	private boolean existsById(int id) {
		log.debug("Id for checking existence = {}", id);
		return lessonRepository.existsById(id);
	}
	
}
