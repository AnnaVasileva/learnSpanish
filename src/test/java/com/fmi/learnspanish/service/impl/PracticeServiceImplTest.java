package com.fmi.learnspanish.service.impl;

import static org.junit.Assert.assertEquals;
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

import com.fmi.learnspanish.domain.GrammarLevel;
import com.fmi.learnspanish.domain.Lesson;
import com.fmi.learnspanish.domain.MainLevel;
import com.fmi.learnspanish.domain.PracticeLevel;
import com.fmi.learnspanish.domain.Question;
import com.fmi.learnspanish.domain.User;
import com.fmi.learnspanish.domain.VocabularyLevel;
import com.fmi.learnspanish.repository.LessonRepository;
import com.fmi.learnspanish.repository.PracticeLevelRepository;
import com.fmi.learnspanish.repository.QuestionRepository;
import com.fmi.learnspanish.repository.UserRepository;
import com.fmi.learnspanish.service.SessionService;
import com.fmi.learnspanish.web.resource.QuestionResource;

public class PracticeServiceImplTest {

	private static final String USERNAME = "testUsername";
	private static final String EMAIL = "testEmail";
	private static final String LESSON_ID = "223ce875-efe2-4709-a641-b5b493b2f79f";
	private static final int LESSON_NUMBER = 1;
	private static final int NEXT_LESSON_NUMBER = 2;
	private static final String LESSON_TITLE = "Beginner Level Lesson";
	private static final String QUESTION_CONTENT = "Test question content";
	private static final String CORRECT_ANSWER = "Correct answer";
	private static final String WRONG_OPTION1 = "Wrong option1";
	private static final String WRONG_OPTION2 = "Wrong option2";
	private static final String WRONG_OPTION3 = "Wrong option3";

	@InjectMocks
	PracticeServiceImpl practiceServiceImpl;

	@Mock
	LessonRepository lessonRepository;

	@Mock
	PracticeLevelRepository practiceLevelRepository;

	@Mock
	QuestionRepository questionRepository;

	@Mock
	UserRepository userRepository;

	@Mock
	SessionService sessionService;

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
	public void createPracticeLevelBeginnerOk() {
		Lesson lesson = createLesson();
		PracticeLevel expectedResult = new PracticeLevel();
		expectedResult.setLesson(lesson);
		expectedResult.setLevel(lesson.getLessonNumber());

		when(lessonRepository.findByLevelAndLessonNumber(MainLevel.BEGINNER, LESSON_NUMBER))//
				.thenReturn(lesson);

		PracticeLevel actualResult = practiceServiceImpl.createPracticeLevel(MainLevel.BEGINNER);
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void createPracticeLevelIntermidiateOk() {
		Lesson lesson = createLesson();
		PracticeLevel expectedResult = new PracticeLevel();
		expectedResult.setLesson(lesson);
		expectedResult.setLevel(lesson.getLessonNumber());

		when(lessonRepository.findByLevelAndLessonNumber(MainLevel.INTERMIDIATE, LESSON_NUMBER))//
				.thenReturn(lesson);

		PracticeLevel actualResult = practiceServiceImpl.createPracticeLevel(MainLevel.INTERMIDIATE);
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void getQuestionsOk() {
		HttpSession session = new MockHttpSession();
		session.setAttribute("level", MainLevel.BEGINNER);

		Lesson lesson = createLesson();

		Question question = new Question();
		question.setLesson(lesson);
		question.setContent(QUESTION_CONTENT);
		question.setCorrectAnswer(CORRECT_ANSWER);
		question.setWrongOption1(WRONG_OPTION1);
		question.setWrongOption2(WRONG_OPTION2);
		question.setWrongOption3(WRONG_OPTION3);
		List<Question> questionsList = Collections.singletonList(question);

		when(lessonRepository.findByLevelAndLessonNumber(MainLevel.BEGINNER, LESSON_NUMBER))//
				.thenReturn(lesson);
		when(questionRepository.findAllByLessonId(lesson.getId())).thenReturn(questionsList);

		Set<String> choices = new HashSet<>();
		choices.add(CORRECT_ANSWER);
		choices.add(WRONG_OPTION1);
		choices.add(WRONG_OPTION2);
		choices.add(WRONG_OPTION3);
		QuestionResource questionResource = new QuestionResource();
		questionResource.setText(QUESTION_CONTENT);
		questionResource.setChoices(choices);
		questionResource.setAnswer(CORRECT_ANSWER);
		List<QuestionResource> expectedResult = Collections.singletonList(questionResource);

		List<QuestionResource> actualResult = practiceServiceImpl.getQuestions(session, LESSON_NUMBER);
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void practiceUpOk() {
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

		practiceServiceImpl.practiceUp(session, LESSON_NUMBER);
		assertEquals(NEXT_LESSON_NUMBER, user.getPracticeLevel().getLevel());
		assertEquals(nextLesson, user.getPracticeLevel().getLesson());
	}
	
	@Test
	public void practiceInvalidLessonNumberOk() {
		User user = createUser();
		HttpSession session = new MockHttpSession();
		session.setAttribute("email", EMAIL);
		Lesson sameLesson = createLesson();

		when(userRepository.findByEmail(EMAIL)).thenReturn(user);

		practiceServiceImpl.practiceUp(session, NEXT_LESSON_NUMBER);
		assertEquals(LESSON_NUMBER, user.getPracticeLevel().getLevel());
		assertEquals(sameLesson, user.getPracticeLevel().getLesson());
	}

	@Test
	public void practiceUpNoNextLesson() {
		User user = createUser();
		Lesson lesson = createLesson();
		HttpSession session = new MockHttpSession();
		session.setAttribute("email", EMAIL);

		when(userRepository.findByEmail(EMAIL)).thenReturn(user);
		when(lessonRepository.findByLevelAndLessonNumber(MainLevel.BEGINNER, NEXT_LESSON_NUMBER))//
				.thenReturn(null);

		practiceServiceImpl.practiceUp(session, LESSON_NUMBER);
		assertEquals(LESSON_NUMBER, user.getPracticeLevel().getLevel());
		assertEquals(lesson, user.getPracticeLevel().getLesson());
	}

}
