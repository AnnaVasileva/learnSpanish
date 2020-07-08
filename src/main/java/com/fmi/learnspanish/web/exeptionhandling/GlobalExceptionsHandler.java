package com.fmi.learnspanish.web.exeptionhandling;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionsHandler {

	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("errors/error.html");
		modelAndView.addObject("message", ex.getMessage());
		return modelAndView;
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ModelAndView handleException(UserNotFoundException ex) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("errors/error.html");
		modelAndView.addObject("message", ex.getMessage());
		return modelAndView;
	}

	@ExceptionHandler(InvalidUserException.class)
	public ModelAndView handleException(InvalidUserException ex) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("errors/error.html");
		modelAndView.addObject("message", ex.getMessage());
		return modelAndView;
	}
	
	@ExceptionHandler(AdminAlreadyExistsException.class)
	public ModelAndView handleException(AdminAlreadyExistsException ex) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("errors/errorAdmin.html");
		modelAndView.addObject("message", ex.getMessage());
		return modelAndView;
	}
}
