package com.fmi.learnspanish.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.ModelAndView;

import com.fmi.learnspanish.domain.VocabularyCategoryType;
import com.fmi.learnspanish.service.VocabularyService;
import com.fmi.learnspanish.service.WordService;
import com.fmi.learnspanish.web.resource.FlashcardResource;

public class VocabularyControllerTest {

	private static final int LESSON_NUMBER = 1;
	private static final String FLASHCARD_FRONT = "testFront";
	private static final String FLASHCARD_BACK = "testBack";

	@InjectMocks
	VocabularyController vocabularyController;

	@Mock
	VocabularyService vocabularyService;

	@Mock
	WordService wordService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getVocabularyLessonDecksOk() {
		ModelAndView modelAndView = new ModelAndView();
		String expectedNameResult = "learn/vocabulary-menu.html";
		String expectedKeyResult = "currentLesson";
		int expextedValueResult = LESSON_NUMBER;

		ModelAndView actualResult = vocabularyController.getVocabularyLessonDecks(LESSON_NUMBER, modelAndView);
		assertEquals(expectedNameResult, actualResult.getViewName());
		assertTrue(actualResult.getModel().containsKey(expectedKeyResult));
		assertTrue(actualResult.getModel().containsValue(expextedValueResult));
	}

	@Test
	public void getDeckAntonyms() {
		testGetDeckByCategory(VocabularyCategoryType.ANTONYMS.toString());
	}

	@Test
	public void getDeckTranslations() {
		testGetDeckByCategory(VocabularyCategoryType.TRANSLATIONS.toString());
	}

	@Test
	public void getDeckPictures() {
		testGetDeckByCategory(VocabularyCategoryType.PICTURES.toString());
	}

	private void testGetDeckByCategory(String categoryType) {
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = new MockHttpSession();

		FlashcardResource flashcardResource = new FlashcardResource();
		flashcardResource.setFront(FLASHCARD_FRONT);
		flashcardResource.setBack(FLASHCARD_BACK);
		List<FlashcardResource> deck = Collections.singletonList(flashcardResource);

		String expectedNameResult = "learnCategories/" + categoryType + ".html";
		String expectedDeckKeyResult = "deck";
		List<FlashcardResource> expextedDeckValueResult = deck;
		String expectedLessonKeyResult = "currentLesson";
		int expextedLessonValueResult = LESSON_NUMBER;

		when(wordService.getCards(session, categoryType, LESSON_NUMBER)).thenReturn(deck);

		ModelAndView actualResult = vocabularyController.getDeck(categoryType, LESSON_NUMBER, session, modelAndView);
		assertEquals(expectedNameResult, actualResult.getViewName());
		assertTrue(actualResult.getModel().containsKey(expectedDeckKeyResult));
		assertTrue(actualResult.getModel().containsValue(expextedDeckValueResult));
		assertTrue(actualResult.getModel().containsKey(expectedLessonKeyResult));
		assertTrue(actualResult.getModel().containsValue(expextedLessonValueResult));
	}

}
