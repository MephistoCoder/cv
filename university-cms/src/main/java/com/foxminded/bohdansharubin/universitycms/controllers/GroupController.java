package com.foxminded.bohdansharubin.universitycms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foxminded.bohdansharubin.universitycms.services.GroupService;

@Controller
@RequestMapping("/user")
public class GroupController {
	
	@Autowired
	private GroupService groupService;
	
	@GetMapping("/groups")
	public String groups(Model model) {
		model.addAttribute("groups", groupService.findAll());
		return "groups_page";
	}
}
