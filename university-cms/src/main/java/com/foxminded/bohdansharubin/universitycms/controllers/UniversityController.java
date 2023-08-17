package com.foxminded.bohdansharubin.universitycms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UniversityController {
	
	@RequestMapping("/user/home")
	public String home() {
		return "home_page";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "signin_page";
	}
	
}
