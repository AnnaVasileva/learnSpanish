// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.web.exeptionhandling;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionsHandler {

	private static final String ERROR_VIEW_NAME = "errors/error.html";
	private static final String MESSAGE = "message";

	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(ERROR_VIEW_NAME);
		modelAndView.addObject(MESSAGE, ex.getMessage());
		return modelAndView;
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ModelAndView handleException(UserNotFoundException ex) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(ERROR_VIEW_NAME);
		modelAndView.addObject(MESSAGE, ex.getMessage());
		return modelAndView;
	}

	@ExceptionHandler(InvalidUserException.class)
	public ModelAndView handleException(InvalidUserException ex) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(ERROR_VIEW_NAME);
		modelAndView.addObject(MESSAGE, ex.getMessage());
		return modelAndView;
	}
	
	@ExceptionHandler(StatisticsNotFoundException.class)
	public ModelAndView handleException(StatisticsNotFoundException ex) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(ERROR_VIEW_NAME);
		modelAndView.addObject(MESSAGE, ex.getMessage());
		return modelAndView;
	}
	
	@ExceptionHandler(AdminAlreadyExistsException.class)
	public ModelAndView handleException(AdminAlreadyExistsException ex) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("errors/errorAdmin.html");
		modelAndView.addObject(MESSAGE, ex.getMessage());
		return modelAndView;
	}
	
	@ExceptionHandler(LessonAlreadyExistsException.class)
	public ModelAndView handleException(LessonAlreadyExistsException ex) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("errors/errorLesson.html");
		modelAndView.addObject(MESSAGE, ex.getMessage());
		return modelAndView;
	}
	
	
}
