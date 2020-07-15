package com.fmi.learnspanish.web.controller;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import com.fmi.learnspanish.service.AdminService;
import com.fmi.learnspanish.web.exeptionhandling.AdminAlreadyExistsException;
import com.fmi.learnspanish.web.exeptionhandling.StatisticsNotFoundException;
import com.fmi.learnspanish.web.exeptionhandling.UserNotFoundException;
import com.fmi.learnspanish.web.resource.MakeAdminResource;
import com.fmi.learnspanish.web.resource.UserStatisticsResource;

public class AdminControllerTest {

	private static final String USERNAME = "testUsername";
	private static final String EMAIL = "testEmail";
	private static final String LESSON_TITLE = "Beginner Level Lesson";
	private static final String CAPITALIZED_MAIN_LEVEL = "Beginner";
	private static final String EXCEPTION_MESSAGE = "testExceptionMessage";

	@InjectMocks
	AdminController adminController;

	@Mock
	AdminService adminService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getUsersStatisticsOk() throws StatisticsNotFoundException {
		ModelAndView modelAndView = new ModelAndView();
		UserStatisticsResource userStatisticsResource = new UserStatisticsResource();
		userStatisticsResource.setUsername(USERNAME);
		userStatisticsResource.setMainLevel(CAPITALIZED_MAIN_LEVEL);
		userStatisticsResource.setGrammarLevel(LESSON_TITLE);
		userStatisticsResource.setVocabularyLevel(LESSON_TITLE);
		userStatisticsResource.setPracticeLevel(LESSON_TITLE);

		String expectedNameResult = "admin/usersStatistics.html";
		String expectedKeyResult = "usersStatistics";
		List<UserStatisticsResource> expextedValueResult = Collections.singletonList(userStatisticsResource);

		when(adminService.getUsersStatistics()).thenReturn(expextedValueResult);

		ModelAndView actualResult = adminController.getUsersStatistics(modelAndView);

		assertEquals(expectedNameResult, actualResult.getViewName());
		assertTrue(actualResult.getModel().containsKey(expectedKeyResult));
		assertTrue(actualResult.getModel().containsValue(expextedValueResult));
	}

	@Test
	public void getUsersStatisticsThrowStatisticsNotFoundException() throws StatisticsNotFoundException {
		ModelAndView modelAndView = new ModelAndView();
		when(adminService.getUsersStatistics()).thenThrow(StatisticsNotFoundException.class);
		assertThatThrownBy(() -> adminController.getUsersStatistics(modelAndView))
				.isInstanceOf(StatisticsNotFoundException.class);
	}

	@Test
	public void getRegisterFormOk() {
		ModelAndView modelAndView = new ModelAndView();
		String expectedResult = "admin/makeAdmin.html";
		ModelAndView actualResult = adminController.getRegisterForm(modelAndView);
		assertEquals(expectedResult, actualResult.getViewName());
	}

	@Test
	public void makeAdminOk() throws AdminAlreadyExistsException, UserNotFoundException {
		MakeAdminResource makeAdminResource = new MakeAdminResource();
		makeAdminResource.setUsername(USERNAME);
		makeAdminResource.setEmail(EMAIL);
		String expectedResult = "admin/makeAdminToastr.html";

		ModelAndView actualResult = adminController.makeAdmin(makeAdminResource);
		assertEquals(expectedResult, actualResult.getViewName());
	}

	@Test
	public void handleExceptionOk() {
		UserNotFoundException exception = new UserNotFoundException(EXCEPTION_MESSAGE);

		String expectedNameResult = "errors/errorAdmin.html";
		String expectedKeyResult = "message";
		String expextedValueResult = EXCEPTION_MESSAGE;

		ModelAndView actualResult = adminController.handleException(exception);
		assertEquals(expectedNameResult, actualResult.getViewName());
		assertTrue(actualResult.getModel().containsKey(expectedKeyResult));
		assertTrue(actualResult.getModel().containsValue(expextedValueResult));
	}

}
