package com.fmi.learnspanish.web.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fmi.learnspanish.service.AuthenticatedUserService;

@RestController
@RequestMapping("/")
public class HomeController {

	@Autowired
	private AuthenticatedUserService authenticatedUserService;

	@GetMapping("")
	public ModelAndView getIndex(ModelAndView modelAndView) {
		modelAndView.setViewName("home/index.html");
		return modelAndView;
	}

	@GetMapping("home")
	public ModelAndView getHome(ModelAndView modelAndView) {
		List<String> authorities = authenticatedUserService.getRoles();
		System.out.printf("authorities - > %s%n", authorities);
		modelAndView.setViewName("home/home.html");
		return modelAndView;
	}

}