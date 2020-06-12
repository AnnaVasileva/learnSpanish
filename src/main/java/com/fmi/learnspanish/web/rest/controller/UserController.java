package com.fmi.learnspanish.web.rest.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fmi.learnspanish.service.UserService;
import com.fmi.learnspanish.web.rest.resource.UserStatisticsResource;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/profile/{email}")
	public ModelAndView getUserProfile(@PathVariable String email, ModelAndView modelAndView, HttpSession session) {
		modelAndView.setViewName("user/profile.html");
		return modelAndView;
	}

	@GetMapping("/statistics")
	//@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView getUserProfile(ModelAndView modelAndView) {
		List<UserStatisticsResource> usersStatistics = userService.getUsersStatistics();
		modelAndView.addObject("usersStatistics", usersStatistics);
		modelAndView.setViewName("statistics/usersStatistics.html");
		return modelAndView;
	}

}
