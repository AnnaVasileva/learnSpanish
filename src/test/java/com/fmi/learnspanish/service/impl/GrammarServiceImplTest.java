package com.fmi.learnspanish.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;

import com.fmi.learnspanish.domain.GrammarLevel;
import com.fmi.learnspanish.domain.Lesson;
import com.fmi.learnspanish.domain.MainLevel;
import com.fmi.learnspanish.domain.PracticeLevel;
import com.fmi.learnspanish.domain.User;
import com.fmi.learnspanish.domain.VocabularyLevel;
import com.fmi.learnspanish.repository.GrammarLevelRepository;
import com.fmi.learnspanish.repository.LessonRepository;
import com.fmi.learnspanish.repository.UserRepository;
import com.fmi.learnspanish.service.SessionService;

public class GrammarServiceImplTest {

	private static final String USERNAME = "testUsername";
	private static final String EMAIL = "testEmail";
	private static final int LESSON_NUMBER = 1;
	private static final int NEXT_LESSON_NUMBER = 2;
	private static final String LESSON_TITLE = "Beginner Level Lesson";
	private static final String PATH_FOR_LESSON_CONTENT = "d:/MasterProject/grammarLesson_1.txt";

	@InjectMocks
	GrammarServiceImpl grammarServiceImpl;

	@Mock
	LessonRepository lessonRepository;

	@Mock
	GrammarLevelRepository grammarLevelRepository;

	@Mock
	SessionService sessionService;

	@Mock
	UserRepository userRepository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	private Lesson createLesson() {
		Lesson lesson = new Lesson();
		lesson.setLessonNumber(LESSON_NUMBER);
		lesson.setLevel(MainLevel.BEGINNER);
		lesson.setTitle(LESSON_TITLE);
		return lesson;
	}

	private User createUser() {
		User user = new User();
		user.setUsername(USERNAME);
		user.setEmail(EMAIL);
		user.setLevel(MainLevel.BEGINNER);

		Lesson lesson = createLesson();

		GrammarLevel grammarLevel = new GrammarLevel();
		grammarLevel.setLevel(1);
		grammarLevel.setLesson(lesson);

		user.setGrammarLevel(grammarLevel);

		VocabularyLevel vocabularyLevel = new VocabularyLevel();
		vocabularyLevel.setLevel(1);
		vocabularyLevel.setLesson(lesson);
		user.setVocabularyLevel(vocabularyLevel);

		PracticeLevel practiceLevel = new PracticeLevel();
		practiceLevel.setLevel(1);
		practiceLevel.setLesson(lesson);
		user.setPracticeLevel(practiceLevel);

		return user;
	}

	@Test
	public void createGrammarLevelBeginnerOk() {
		Lesson lesson = createLesson();
		GrammarLevel expectedResult = new GrammarLevel();
		expectedResult.setLesson(lesson);
		expectedResult.setLevel(lesson.getLessonNumber());

		when(lessonRepository.findByLevelAndLessonNumber(MainLevel.BEGINNER, LESSON_NUMBER))//
				.thenReturn(lesson);

		GrammarLevel actualResult = grammarServiceImpl.createGrammarLevel(MainLevel.BEGINNER);
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void createGrammarLevelIntermidiateOk() {
		Lesson lesson = createLesson();
		GrammarLevel expectedResult = new GrammarLevel();
		expectedResult.setLesson(lesson);
		expectedResult.setLevel(lesson.getLessonNumber());

		when(lessonRepository.findByLevelAndLessonNumber(MainLevel.INTERMIDIATE, LESSON_NUMBER))//
				.thenReturn(lesson);

		GrammarLevel actualResult = grammarServiceImpl.createGrammarLevel(MainLevel.INTERMIDIATE);
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void setLessonGrammarOk() {
		HttpSession session = new MockHttpSession();
		session.setAttribute("level", MainLevel.BEGINNER);

		Lesson lesson = createLesson();
		lesson.setContent(PATH_FOR_LESSON_CONTENT);
		when(lessonRepository.findByLevelAndLessonNumber(MainLevel.BEGINNER, LESSON_NUMBER))//
				.thenReturn(lesson);

		grammarServiceImpl.setLessonGrammar(session, LESSON_NUMBER);
		assertEquals(LESSON_NUMBER, session.getAttribute("currentLessonNumber"));
		assertEquals(LESSON_TITLE, session.getAttribute("currentLessonTitle"));

	}

	@Test
	public void grammarUpOk() {
		User user = createUser();
		HttpSession session = new MockHttpSession();
		session.setAttribute("email", EMAIL);

		Lesson nextLesson = new Lesson();
		nextLesson.setLessonNumber(NEXT_LESSON_NUMBER);
		nextLesson.setLevel(MainLevel.BEGINNER);
		String nextLessonTitle = "nextLessonTitle";
		nextLesson.setTitle(nextLessonTitle);

		when(userRepository.findByEmail(EMAIL)).thenReturn(user);
		when(lessonRepository.findByLevelAndLessonNumber(MainLevel.BEGINNER, NEXT_LESSON_NUMBER))//
				.thenReturn(nextLesson);

		grammarServiceImpl.grammarUp(session);
		assertEquals(NEXT_LESSON_NUMBER, user.getGrammarLevel().getLevel());
		assertEquals(nextLesson, user.getGrammarLevel().getLesson());
	}

	@Test
	public void grammarUpNoNextLesson() {
		User user = createUser();
		Lesson lesson = createLesson();
		HttpSession session = new MockHttpSession();
		session.setAttribute("email", EMAIL);

		when(userRepository.findByEmail(EMAIL)).thenReturn(user);
		when(lessonRepository.findByLevelAndLessonNumber(MainLevel.BEGINNER, NEXT_LESSON_NUMBER))//
				.thenReturn(null);

		grammarServiceImpl.grammarUp(session);
		assertEquals(LESSON_NUMBER, user.getGrammarLevel().getLevel());
		assertEquals(lesson, user.getGrammarLevel().getLesson());
	}

}
