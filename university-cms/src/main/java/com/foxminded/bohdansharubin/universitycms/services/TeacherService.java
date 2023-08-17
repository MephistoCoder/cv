package com.foxminded.bohdansharubin.universitycms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.bohdansharubin.universitycms.convertor.ModelConvertor;
import com.foxminded.bohdansharubin.universitycms.dto.NullPersonDTO;
import com.foxminded.bohdansharubin.universitycms.dto.PersonDTO;
import com.foxminded.bohdansharubin.universitycms.models.Course;
import com.foxminded.bohdansharubin.universitycms.models.Teacher;
import com.foxminded.bohdansharubin.universitycms.repositories.TeacherRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TeacherService extends AbstractPersonService {
	
	private TeacherRepository teacherRepository;
	
	@Autowired
	public TeacherService(TeacherRepository teacherRepository,
		ModelConvertor modelConvertor) {
		super(modelConvertor);
		this.teacherRepository = teacherRepository;
	}
	
	@Transactional
	public PersonDTO save(PersonDTO dto) {
		Teacher teacherForSaving = modelConvertor.convertToTeacher(dto);
		if(!teacherForSaving.isValid()) {
			log.warn("Can't create teacher incorrect data");
			return new NullPersonDTO();
		}
		log.info("Created teacher = {}", teacherForSaving);
		return modelConvertor.convertToPersonDto((teacherRepository.save(teacherForSaving)));
	}
	
	public PersonDTO findById(int id) {
		log.debug("Id for finding teacher = {}", id);
		return modelConvertor.convertToPersonDto((teacherRepository.getReferenceById(id)));
	}
	
	public List<PersonDTO> findAll() {
		log.debug("Finding all teachers");
		return modelConvertor.convertToPersonDtoList(teacherRepository.findAll());
	}
	
	@Transactional
	public void deleteById(int id) {
		log.debug("Id for deleting teacher = {}", id);
		teacherRepository.deleteById(id);
	}
		
	protected boolean existsById(int id) {
		log.debug("Id for checking existence of teacher = {}", id);
		return teacherRepository.existsById(id);
	}
	
	@Transactional
	public void addTeacherToCourse(Course course, int teacherId) {
		Optional<Teacher> teacherForUpdate = teacherRepository.findById(teacherId);
		teacherForUpdate.ifPresent(teacher -> teacher.addCourse(course));
	}
	
	@Transactional
	public void removeTeacherFromCourse(Course course, int teacherId) {
		Optional<Teacher> teacherForUpdate = teacherRepository.findById(teacherId);
		teacherForUpdate.ifPresent(teacher -> teacher.removeCourse(course));
	}
	
	public void updateCoursesOnTeacher(int teacherId, List<Integer> newCoursesIds) {
//		Teacher teacherForUpdate = teacherRepository.findById(teacherId)
//			.orElseThrow(
//				() -> new NullPointerException());
//		
////		retain all the same courses for both lists
//		List<Course> coursesOfTeacher = teacherForUpdate.getCourses(); 
//		coursesOfTeacher.retainAll(newCourses);
//		
////		if course not exist in teacher then add
//		newCourses.stream()
//			.filter(course -> !coursesOfTeacher.contains(course))
//			.forEach(course -> teacherForUpdate.addCourse(course));
		
	}
	
}
