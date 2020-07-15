package com.fmi.learnspanish.service.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fmi.learnspanish.repository.UserRepository;
import com.fmi.learnspanish.web.exeptionhandling.InvalidUserException;
import com.fmi.learnspanish.web.resource.RegisterUserResource;

public class AuthValidationServiceImplTest {

	private static final String NOT_MATCHING_PASSWORD = "notMatchingPassword";
	private static final String USERNAME = "testUsername";
	private static final String EMAIL = "testEmail";
	private static final String PASSWORD = "testPassword";

	@InjectMocks
	AuthValidationServiceImpl authenticatedUserServiceImpl;

	@Mock
	UserRepository userRepository;

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
	public void isValidOk() throws InvalidUserException {
		RegisterUserResource registerUserResource = createRegisterUserResource();

		when(userRepository.existsByEmail(EMAIL)).thenReturn(false);

		assertTrue(authenticatedUserServiceImpl.isValid(registerUserResource));
	}

	@Test
	public void isValidAccountTaken() {
		RegisterUserResource registerUserResource = createRegisterUserResource();

		when(userRepository.existsByEmail(EMAIL)).thenReturn(true);

		assertThatThrownBy(() -> authenticatedUserServiceImpl.isValid(registerUserResource))
				.isInstanceOf(InvalidUserException.class);
	}
	
	@Test
	public void isValidNotMatchnigPasswords() {
		RegisterUserResource registerUserResource = createRegisterUserResource();
		registerUserResource.setConfirmPassword(NOT_MATCHING_PASSWORD);

		when(userRepository.existsByEmail(EMAIL)).thenReturn(false);

		assertThatThrownBy(() -> authenticatedUserServiceImpl.isValid(registerUserResource))
				.isInstanceOf(InvalidUserException.class);
	}

}
