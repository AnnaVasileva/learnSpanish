package com.fmi.learnspanish.service.impl;

import static org.junit.Assert.assertEquals;
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

import com.fmi.learnspanish.domain.Lesson;
import com.fmi.learnspanish.domain.MainLevel;
import com.fmi.learnspanish.domain.VocabularyCategoryType;
import com.fmi.learnspanish.domain.Word;
import com.fmi.learnspanish.repository.LessonRepository;
import com.fmi.learnspanish.repository.WordRepository;
import com.fmi.learnspanish.web.resource.FlashcardResource;

public class WordServiceImplTest {

	private static final String LESSON_ID = "223ce875-efe2-4709-a641-b5b493b2f79f";
	private static final int LESSON_NUMBER = 1;
	private static final String LESSON_TITLE = "Beginner Level Lesson";
	private static final String WORD_NAME = "testWordName";
	private static final String WORD_ANTONYM = "testWordAntonym";
	private static final String WORD_TRANSLATION = "testWordATranslation";
	private static final String WORD_PICTURE = "testWordPicture";

	@InjectMocks
	WordServiceImpl wordServiceImpl;

	@Mock
	LessonRepository lessonRepository;

	@Mock
	private WordRepository wordRepository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	private Lesson createLesson() {
		Lesson lesson = new Lesson();
		lesson.setId(LESSON_ID);
		lesson.setLessonNumber(LESSON_NUMBER);
		lesson.setLevel(MainLevel.BEGINNER);
		lesson.setTitle(LESSON_TITLE);
		return lesson;
	}

	private List<Word> createWordList() {
		Word word = new Word();
		word.setName(WORD_NAME);
		word.setAntonym(WORD_ANTONYM);
		word.setTranslation(WORD_TRANSLATION);
		word.setPicture(WORD_PICTURE);
		return Collections.singletonList(word);
	}

	private List<FlashcardResource> createExpectedResult(String cardBack) {
		FlashcardResource flashcardResource = new FlashcardResource();
		flashcardResource.setFront(WORD_NAME);
		flashcardResource.setBack(cardBack);
		return Collections.singletonList(flashcardResource);
	}

	@Test
	public void getCardsAntonyms() {
		HttpSession session = new MockHttpSession();
		session.setAttribute("level", MainLevel.BEGINNER);
		Lesson lesson = createLesson();
		List<Word> words = createWordList();

		List<FlashcardResource> expectedResult = createExpectedResult(WORD_ANTONYM);

		when(lessonRepository.findByLevelAndLessonNumber(MainLevel.BEGINNER, LESSON_NUMBER))//
				.thenReturn(lesson);
		when(wordRepository.findAllByLessonId(lesson.getId())).thenReturn(words);

		List<FlashcardResource> actualResult = wordServiceImpl.getCards(session,
				VocabularyCategoryType.ANTONYMS.toString(), LESSON_NUMBER);
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void getCardsTranslations() {
		HttpSession session = new MockHttpSession();
		session.setAttribute("level", MainLevel.BEGINNER);
		Lesson lesson = createLesson();
		List<Word> words = createWordList();

		List<FlashcardResource> expectedResult = createExpectedResult(WORD_TRANSLATION);

		when(lessonRepository.findByLevelAndLessonNumber(MainLevel.BEGINNER, LESSON_NUMBER))//
				.thenReturn(lesson);
		when(wordRepository.findAllByLessonId(lesson.getId())).thenReturn(words);

		List<FlashcardResource> actualResult = wordServiceImpl.getCards(session,
				VocabularyCategoryType.TRANSLATIONS.toString(), LESSON_NUMBER);
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void getCardsPicturess() {
		HttpSession session = new MockHttpSession();
		session.setAttribute("level", MainLevel.BEGINNER);
		Lesson lesson = createLesson();
		List<Word> words = createWordList();

		List<FlashcardResource> expectedResult = createExpectedResult(WORD_PICTURE);

		when(lessonRepository.findByLevelAndLessonNumber(MainLevel.BEGINNER, LESSON_NUMBER))//
				.thenReturn(lesson);
		when(wordRepository.findAllByLessonId(lesson.getId())).thenReturn(words);

		List<FlashcardResource> actualResult = wordServiceImpl.getCards(session,
				VocabularyCategoryType.PICTURES.toString(), LESSON_NUMBER);
		assertEquals(expectedResult, actualResult);
	}

}
