package com.fmi.learnspanish.web.rest.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {

	@GetMapping("/profile/{email}")
	public ModelAndView getUserProfile(@PathVariable String email, ModelAndView modelAndView, HttpSession session) {
		modelAndView.setViewName("user/profile.html");
		return modelAndView;
	}
}
