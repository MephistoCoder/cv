package com.foxminded.bohdansharubin.universitycms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foxminded.bohdansharubin.universitycms.services.LessonTimeService;

@Controller
@RequestMapping("/user")
public class LessonTimeController {

	@Autowired
	private LessonTimeService lessonTimeService;
	
	@GetMapping("/lessons_times")
	public String lessonsTimes(Model model) {
		model.addAttribute("lessonsTimes", lessonTimeService.findAll());
		return "lessons_times_page";
	}
}
