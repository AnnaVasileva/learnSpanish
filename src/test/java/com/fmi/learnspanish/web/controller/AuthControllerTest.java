package com.fmi.learnspanish.web.controller;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import com.fmi.learnspanish.service.AuthValidationService;
import com.fmi.learnspanish.service.UserService;
import com.fmi.learnspanish.web.exeptionhandling.InvalidUserException;
import com.fmi.learnspanish.web.resource.RegisterUserResource;

public class AuthControllerTest {

	private static final String USERNAME = "testUsername";
	private static final String EMAIL = "testEmail";
	private static final String PASSWORD = "testPassword";

	@InjectMocks
	AuthController authController;

	@Mock
	AuthValidationService authValidationService;

	@Mock
	UserService userService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	private RegisterUserResource createRegisterUserResource() {
		RegisterUserResource registerUserResource = new RegisterUserResource();
		registerUserResource.setUsername(USERNAME);
		registerUserResource.setEmail(EMAIL);
		registerUserResource.setPassword(PASSWORD);
		registerUserResource.setConfirmPassword(PASSWORD);
		return registerUserResource;
	}

	@Test
	public void getLoginFormOk() {
		ModelAndView modelAndView = new ModelAndView();
		String expectedResult = "auth/login.html";
		ModelAndView actualResult = authController.getLoginForm(modelAndView);
		assertEquals(expectedResult, actualResult.getViewName());
	}

	@Test
	public void getRegisterFormOk() {
		ModelAndView modelAndView = new ModelAndView();
		String expectedResult = "auth/register.html";
		ModelAndView actualResult = authController.getRegisterForm(modelAndView);
		assertEquals(expectedResult, actualResult.getViewName());
	}

	@Test
	public void registerTrue() throws InvalidUserException {
		registerTest(true);
	}

	@Test
	public void registerFalse() throws InvalidUserException {
		registerTest(false);
	}

	private void registerTest(boolean isValid) throws InvalidUserException {
		RegisterUserResource registerUserResource = createRegisterUserResource();
		String expectedResult = "redirect:/users/login";

		when(authValidationService.isValid(registerUserResource)).thenReturn(isValid);

		ModelAndView actualResult = authController.register(registerUserResource);
		assertEquals(expectedResult, actualResult.getViewName());
	}

	@Test
	public void registerThrowInvalidUserException() throws InvalidUserException {
		RegisterUserResource registerUserResource = createRegisterUserResource();
		when(authValidationService.isValid(registerUserResource)).thenThrow(InvalidUserException.class);
		assertThatThrownBy(() -> authController.register(registerUserResource))
				.isInstanceOf(InvalidUserException.class);
	}

}
