package com.foxminded.bohdansharubin.universitycms.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.foxminded.bohdansharubin.universitycms.dto.CourseDTO;
import com.foxminded.bohdansharubin.universitycms.dto.PersonDTO;
import com.foxminded.bohdansharubin.universitycms.services.CourseService;
import com.foxminded.bohdansharubin.universitycms.services.TeacherService;

@Controller
@RequestMapping("/user")
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private TeacherService teacherService;
	
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	ResponseEntity<Object> onConstraintValidationException(
		ConstraintViolationException e) {
		List<String> violations = new ArrayList<>();
	    for (ConstraintViolation violation : e.getConstraintViolations()) {
	      violations.add(violation.getPropertyPath().toString() + " = " + violation.getMessage());
	    }
	    return ResponseEntity.badRequest().body(violations);
	 }
	
	@GetMapping("/courses")
	public String courses(Model model, Authentication auth) {
		model.addAttribute("courses", courseService.findAll());
		model.addAttribute("courseDTO", new CourseDTO());
		
		String authorityName = auth.getAuthorities()
			.stream()
			.map(GrantedAuthority::getAuthority)
			.findFirst()
			.orElse(null);
		
		model.addAttribute("authority", authorityName);
		return "courses_page";
	}
	
	@PostMapping("/edit-course")
	public String editCourse(CourseDTO dto, Model model) {
		List<PersonDTO> allTeachers = teacherService.findAll();
		CourseDTO courseDTO = courseService.findById(dto.getId());
		
		List<PersonDTO> teachersOfCourse = allTeachers.stream()
			.filter(teacher -> courseDTO.getTeachers().contains(teacher.getId()))
			.collect(Collectors.toList());
		allTeachers.removeAll(teachersOfCourse);
		
		model.addAttribute("teachers", allTeachers);
		model.addAttribute("teachersFromCourse", teachersOfCourse);

		return "edit_course_page";
	}
	
	@GetMapping("/edit-course")
	public String newCourse(Model model) {	
		model.addAttribute("teachers", teacherService.findAll());
		model.addAttribute("courseDTO", new CourseDTO());
		return "edit_course_page";
	}
	
	@DeleteMapping("/delete-course")
	public String delete(int id) {
		courseService.deleteById(id);
		return "redirect:/user/courses";
	}
	
	@PostMapping("/update-course")
	public String update(CourseDTO dto) {
		courseService.update(dto);
		return "redirect:/user/courses";
	}
	
	@PostMapping("/save-course")
	public String save(CourseDTO dto) {
		courseService.save(dto);
		return "redirect:/user/courses";
	}
	
	
}
