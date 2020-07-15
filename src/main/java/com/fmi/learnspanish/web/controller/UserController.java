package com.fmi.learnspanish.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

	@GetMapping("/profile/{email}")
	public ModelAndView getUserProfile(@PathVariable String email, ModelAndView modelAndView) {
		modelAndView.setViewName("user/profile.html");
		return modelAndView;
	}
}
