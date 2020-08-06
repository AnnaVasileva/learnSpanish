// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
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