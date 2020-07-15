package com.fmi.learnspanish.web.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

public class LearnControllerTest {

	private static final String EMAIL = "testEmail";

	@InjectMocks
	LearnController learnController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getGrammarOk() {
		ModelAndView modelAndView = new ModelAndView();
		String expectedResult = "learn/grammar.html";
		ModelAndView actualResult = learnController.getGrammar(EMAIL, modelAndView);
		assertEquals(expectedResult, actualResult.getViewName());
	}

	@Test
	public void getVocabularyOk() {
		ModelAndView modelAndView = new ModelAndView();
		String expectedResult = "learn/vocabulary.html";
		ModelAndView actualResult = learnController.getVocabulary(EMAIL, modelAndView);
		assertEquals(expectedResult, actualResult.getViewName());
	}

	@Test
	public void getPracticeOk() {
		ModelAndView modelAndView = new ModelAndView();
		String expectedResult = "learn/practice.html";
		ModelAndView actualResult = learnController.getPractice(EMAIL, modelAndView);
		assertEquals(expectedResult, actualResult.getViewName());
	}

}
