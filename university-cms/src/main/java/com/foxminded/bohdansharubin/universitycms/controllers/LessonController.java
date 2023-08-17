package com.foxminded.bohdansharubin.universitycms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class LessonController {
	
	@GetMapping("/schedule")
	public String schedule() {
		return "schedule_page";
	}
	
}
