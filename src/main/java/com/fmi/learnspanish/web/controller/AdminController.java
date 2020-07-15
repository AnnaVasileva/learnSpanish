package com.fmi.learnspanish.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fmi.learnspanish.service.AdminService;
import com.fmi.learnspanish.web.exeptionhandling.AdminAlreadyExistsException;
import com.fmi.learnspanish.web.exeptionhandling.StatisticsNotFoundException;
import com.fmi.learnspanish.web.exeptionhandling.UserNotFoundException;
import com.fmi.learnspanish.web.resource.MakeAdminResource;
import com.fmi.learnspanish.web.resource.UserStatisticsResource;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@GetMapping("/statistics")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ModelAndView getUsersStatistics(ModelAndView modelAndView) throws StatisticsNotFoundException {
		List<UserStatisticsResource> usersStatistics = adminService.getUsersStatistics();
		modelAndView.setViewName("admin/usersStatistics.html");
		modelAndView.addObject("usersStatistics", usersStatistics);
		return modelAndView;
	}

	@GetMapping("/makeAdmin")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ModelAndView getRegisterForm(ModelAndView modelAndView) {
		modelAndView.setViewName("admin/makeAdmin.html");
		return modelAndView;
	}

	@PostMapping("/makeAdmin")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ModelAndView makeAdmin(@ModelAttribute MakeAdminResource model)
			throws AdminAlreadyExistsException, UserNotFoundException {
		adminService.makeAdmin(model);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/makeAdminToastr.html");
		return modelAndView;
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ModelAndView handleException(UserNotFoundException ex) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("errors/errorAdmin.html");
		modelAndView.addObject("message", ex.getMessage());
		return modelAndView;
	}

}
