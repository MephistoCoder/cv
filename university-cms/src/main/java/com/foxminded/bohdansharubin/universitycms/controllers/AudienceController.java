package com.foxminded.bohdansharubin.universitycms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foxminded.bohdansharubin.universitycms.services.AudienceService;

@Controller
@RequestMapping("/user")
public class AudienceController {
	
	@Autowired
	private AudienceService audienceService;
	
	@GetMapping("/audiences")
	public String audiences(Model model) {
		model.addAttribute("audiences", audienceService.findAll());
		return "audiences_page";
	}
	
}
