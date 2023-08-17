package com.foxminded.bohdansharubin.universitycms.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foxminded.bohdansharubin.universitycms.dto.UserDTO;
import com.foxminded.bohdansharubin.universitycms.services.UserService;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/admin-panel")
	public String admin(Model model) {
		model.addAttribute("users", userService.findAll());
		model.addAttribute("userDTO", new UserDTO());
		return "admin_page";
	}
	
	@RequestMapping("/edit-user")
	public String editUser(@Valid @ModelAttribute UserDTO userDTO, Model model) {
		model.addAttribute("user", userDTO);
		return "edit_user_page";	
	}
	
	@RequestMapping("/update-user")
	public String updateUser(@Valid @ModelAttribute UserDTO userDTO, Model model) {
		userService.update(userDTO);
		return "redirect:/admin-panel";
	}

}
