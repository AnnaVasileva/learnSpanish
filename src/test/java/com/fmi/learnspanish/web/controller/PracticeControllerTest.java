package com.fmi.learnspanish.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.ModelAndView;

import com.fmi.learnspanish.service.PracticeService;
import com.fmi.learnspanish.web.resource.QuestionResource;

public class PracticeControllerTest {

	private static final int LESSON_NUMBER = 1;
	private static final String QUESTION_CONTENT = "Test question content";
	private static final String CORRECT_ANSWER = "Correct answer";
	private static final String WRONG_OPTION1 = "Wrong option1";
	private static final String WRONG_OPTION2 = "Wrong option2";
	private static final String WRONG_OPTION3 = "Wrong option3";

	@InjectMocks
	PracticeController practiceController;

	@Mock
	PracticeService practiceService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getQuesionsOk() {
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = new MockHttpSession();

		QuestionResource questionResource = new QuestionResource();
		questionResource.setText(QUESTION_CONTENT);
		Set<String> choices = new HashSet<>();
		choices.add(CORRECT_ANSWER);
		choices.add(WRONG_OPTION1);
		choices.add(WRONG_OPTION2);
		choices.add(WRONG_OPTION3);
		questionResource.setChoices(choices);
		questionResource.setAnswer(CORRECT_ANSWER);
		List<QuestionResource> questionResourceList = Collections.singletonList(questionResource);

		when(practiceService.getQuestions(session, LESSON_NUMBER)).thenReturn(questionResourceList);

		String expectedNameResult = "practice-lessons/quiz.html";
		String expectedQuestionKeyResult = "questions";
		List<QuestionResource> expextedQuestionValueResult = questionResourceList;
		String expectedLessonKeyResult = "currentPracticeLessonNumber";
		int expextedLessonValueResult = LESSON_NUMBER;

		ModelAndView actualResult = practiceController.getQuesions(LESSON_NUMBER, session, modelAndView);
		assertEquals(expectedNameResult, actualResult.getViewName());
		assertTrue(actualResult.getModel().containsKey(expectedQuestionKeyResult));
		assertTrue(actualResult.getModel().containsValue(expextedQuestionValueResult));
		assertTrue(actualResult.getModel().containsKey(expectedLessonKeyResult));
		assertTrue(actualResult.getModel().containsValue(expextedLessonValueResult));
	}

	@Test
	public void practiceLevelUpOk() {
		HttpSession session = new MockHttpSession();
		practiceController.practiceLevelUp(LESSON_NUMBER, session);
		verify(practiceService, times(1)).practiceUp(session, LESSON_NUMBER);
	}

}
