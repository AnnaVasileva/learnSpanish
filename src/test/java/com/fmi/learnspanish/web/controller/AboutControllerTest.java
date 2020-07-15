package com.fmi.learnspanish.web.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

public class AboutControllerTest {

	@InjectMocks
	AboutController aboutController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getAboutOk() {
		ModelAndView modelAndView = new ModelAndView();
		String expectedResult = "about/about.html";
		ModelAndView actualResult = aboutController.getAbout(modelAndView);
		assertEquals(expectedResult, actualResult.getViewName());
	}

}
