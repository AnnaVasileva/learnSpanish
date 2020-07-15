package com.fmi.learnspanish.web.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

public class UserControllerTest {

	private static final String EMAIL = "testEmail";

	@InjectMocks
	UserController userController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getUserProfileOk() {
		ModelAndView modelAndView = new ModelAndView();
		String expectedResult = "user/profile.html";
		ModelAndView actualResult = userController.getUserProfile(EMAIL, modelAndView);
		assertEquals(expectedResult, actualResult.getViewName());
	}

}
