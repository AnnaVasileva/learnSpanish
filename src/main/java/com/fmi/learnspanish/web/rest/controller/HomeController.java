package com.fmi.learnspanish.web.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class HomeController {

	@GetMapping("")
	public ModelAndView getIndex(ModelAndView modelAndView) {
		modelAndView.setViewName("home/index.html");
		return modelAndView;
	}

	@GetMapping("home")
	public ModelAndView getHome(ModelAndView modelAndView) {
		modelAndView.setViewName("home/home.html");
		return modelAndView;
	}

}