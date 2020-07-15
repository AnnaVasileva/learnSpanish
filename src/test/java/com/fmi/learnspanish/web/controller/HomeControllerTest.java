package com.fmi.learnspanish.web.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

public class HomeControllerTest {

	@InjectMocks
	HomeController homeController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getIndexOk() {
		ModelAndView modelAndView = new ModelAndView();
		String expectedResult = "home/index.html";
		ModelAndView actualResult = homeController.getIndex(modelAndView);
		assertEquals(expectedResult, actualResult.getViewName());
	}

	@Test
	public void getHomeOk() {
		ModelAndView modelAndView = new ModelAndView();
		String expectedResult = "home/home.html";
		ModelAndView actualResult = homeController.getHome(modelAndView);
		assertEquals(expectedResult, actualResult.getViewName());
	}

}
