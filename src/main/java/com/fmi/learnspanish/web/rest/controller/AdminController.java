package com.fmi.learnspanish.web.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fmi.learnspanish.service.AdminService;
import com.fmi.learnspanish.web.rest.resource.MakeAdminResource;
import com.fmi.learnspanish.web.rest.resource.UserStatisticsResource;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@GetMapping("/statistics")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ModelAndView getUsersStatistics(ModelAndView modelAndView) {
		List<UserStatisticsResource> usersStatistics = adminService.getUsersStatistics();
		modelAndView.addObject("usersStatistics", usersStatistics);
		modelAndView.setViewName("admin/usersStatistics.html");
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
	public ModelAndView makeAdmin(@ModelAttribute MakeAdminResource model) {
		adminService.makeAdmin(model);
		 ModelAndView modelAndView = new ModelAndView();
		 modelAndView.setViewName("admin/makeAdminToastr.html");
		return modelAndView;
	}

}
