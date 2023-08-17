package com.foxminded.bohdansharubin.universitycms.services;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.bohdansharubin.universitycms.convertor.ModelConvertor;
import com.foxminded.bohdansharubin.universitycms.dto.CourseDTO;
import com.foxminded.bohdansharubin.universitycms.exceptions.CourseNotFoundException;
import com.foxminded.bohdansharubin.universitycms.models.Course;
import com.foxminded.bohdansharubin.universitycms.models.Teacher;
import com.foxminded.bohdansharubin.universitycms.repositories.CourseRepository;
import com.foxminded.bohdansharubin.universitycms.repositories.TeacherRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseService {
	
	private CourseRepository courseRepository;
	private TeacherRepository teacherRepository;
	private TeacherService teacherService;
	private ModelConvertor modelConvertor;
	
	@Autowired
	public CourseService(CourseRepository courseRepository,
		ModelConvertor modelConvertor,
		TeacherRepository teacherRepository,
		TeacherService teacherService) {
		this.courseRepository = courseRepository;
		this.teacherRepository = teacherRepository;
		this.modelConvertor = modelConvertor;
		this.teacherService = teacherService;
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@Transactional
	public CourseDTO save(@Valid CourseDTO dto) {
		log.info("{}", dto);
		Course courseForSaving = modelConvertor.dtoToCourse(dto);
		List<Teacher> teachersForUpdate = findTeachersById(dto.getTeachers());
		if(teachersForUpdate.isEmpty()) {
			log.debug("Can't find teachers with such ids = {}", dto.getTeachers());
		}
//		teachersOfCourse.forEach(teacher -> teacher.addCourse(courseForSaving));
		updateTeachersOnCourse(courseForSaving, teachersForUpdate);
//		courseForSaving.setTeachers(teachersForUpdate);
//		teacherService.updateCourseOnTeachersById(courseForSaving, dto.getTeachers());
		log.info("Before save Creating course = {}", courseForSaving);
		courseForSaving = courseRepository.save(courseForSaving);
		log.info("After Save Creating course = {}", courseForSaving);
//		return modelConvertor.courseToDto(courseRepository.save(courseForSaving));
		return modelConvertor.courseToDto(courseForSaving);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@Transactional
	public void deleteById(int id) {
		log.debug("Id ={} for deletion course", id);
		courseRepository.deleteById(id);
	}
	
	public CourseDTO findById(int id) {
		log.debug("Id = {} for finding course", id);
		return modelConvertor.courseToDto(courseRepository.findById(id)
			.orElseThrow(() -> new CourseNotFoundException(id)));
	}
	
	public List<CourseDTO> findAll() {
		log.debug("Finding all courses");
		return modelConvertor.convertToCourseDtoList(courseRepository.findAllByOrderById());
	}
	
//	updating only for teachers
	@PreAuthorize("hasAuthority('ADMIN')")
	@Transactional
	public CourseDTO update(@Valid CourseDTO dto) {
		if(!existsById(dto.getId())) {
			log.warn("Course with id = {} is not exist", dto.getId());
			throw new CourseNotFoundException(dto.getId());
		}
		
		Course courseForUpdating = courseRepository.getReferenceById(dto.getId());
		log.info("Course finding by id = {}", courseForUpdating);
		log.info("Teachers = {}", courseForUpdating.getTeachers());
		List<Teacher> teachersForUpdate = findTeachersById(dto.getTeachers());
		updateTeachersOnCourse(courseForUpdating, teachersForUpdate);
//		teacherService.updateCoursesOnTeacher(0, courseForUpdating);
//		List<Teacher> teachersOfCourse = findTeachersById(dto.getTeachers());
//		log.info("Finding teachers: {}", teachersOfCourse);
//		log.info("Finding teachers size: {}", teachersOfCourse.size());
//		courseForUpdating.getTeachers().retainAll(teachersOfCourse);
		log.info("Updating course with id = {}", dto.getId());
		return modelConvertor.courseToDto(courseRepository.save(courseForUpdating));
	}
	
	private boolean existsById(int id) {
		log.debug("Id for checking existence = {}", id);
		return courseRepository.existsById(id);
	}
	
	private List<Teacher> findTeachersById(List<Integer> teachersIds) {
		log.debug("Finding teachers for course");
		if(teachersIds.isEmpty()) {
			log.warn("Can't find teachers for course, because list of id is empty");
			return new ArrayList<>();
		}
		
		return teacherRepository.findDistinctByIdIn(teachersIds);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private void updateTeachersOnCourse(Course course, List<Teacher> teachers) {
		log.info("new Teachers of course = {}", teachers);
		course.getTeachers()
			.retainAll(teachers);
		log.info("After retain  = {}", course.getTeachers());
		teachers.stream()
			.filter(teacher -> !teacher.getCourses()
				.contains(course))
			.forEach(teacher -> course.addTeacher(teacher));
	}
	
}
