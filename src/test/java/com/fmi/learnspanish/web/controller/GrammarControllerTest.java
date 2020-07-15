package com.fmi.learnspanish.web.controller;

import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.ModelAndView;

import com.fmi.learnspanish.service.GrammarService;

public class GrammarControllerTest {

	private static final String EMAIL = "testEmail";
	private static final int LESSON_NUMBER = 1;

	@InjectMocks
	GrammarController grammarController;

	@Mock
	GrammarService grammarService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getGrammarLessonOk() {
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = new MockHttpSession();
		String expectedResult = "grammar-lessons/grammar-lesson-content.html";
		ModelAndView actualResult = grammarController.getGrammarLesson(LESSON_NUMBER, modelAndView, session);
		assertEquals(expectedResult, actualResult.getViewName());
	}

	@Test
	public void levelUp() {
		HttpSession session = new MockHttpSession();
		session.setAttribute("email", EMAIL);
		String expectedResult = "redirect:/learn/grammar/" + EMAIL;
		ModelAndView actualResult = grammarController.levelUp(session);
		assertEquals(expectedResult, actualResult.getViewName());
	}

}
