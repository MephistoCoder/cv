package com.foxminded.bohdansharubin.universitycms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foxminded.bohdansharubin.universitycms.services.StudentService;

@Controller
@RequestMapping("/user")
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	@GetMapping("/students")
	public String students(Model model) {
		model.addAttribute("students", studentService.findAll());
		return "students_page";			
	}
}
