package com.foxminded.bohdansharubin.universitycms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foxminded.bohdansharubin.universitycms.services.TeacherService;

@Controller
@RequestMapping("/user")
public class TeacherController {

	@Autowired
	private TeacherService teacherService;
	
	@GetMapping("/teachers")
	public String teachers(Model model) {
		model.addAttribute("teachers", teacherService.findAll());
		return "teachers_page";
	}
}
