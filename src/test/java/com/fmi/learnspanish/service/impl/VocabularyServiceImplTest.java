package com.fmi.learnspanish.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
import com.fmi.learnspanish.domain.VocabularyCategory;
import com.fmi.learnspanish.domain.VocabularyCategoryType;
import com.fmi.learnspanish.domain.VocabularyLevel;
import com.fmi.learnspanish.domain.VocabularyStatus;
import com.fmi.learnspanish.repository.LessonRepository;
import com.fmi.learnspanish.repository.UserRepository;
import com.fmi.learnspanish.repository.VocabularyCategoryRepository;
import com.fmi.learnspanish.repository.VocabularyLevelRepository;
import com.fmi.learnspanish.service.SessionService;

public class VocabularyServiceImplTest {

	private static final String USERNAME = "testUsername";
	private static final String EMAIL = "testEmail";
	private static final String LESSON_ID = "223ce875-efe2-4709-a641-b5b493b2f79f";
	private static final int LESSON_NUMBER = 1;
	private static final int NEXT_LESSON_NUMBER = 2;
	private static final String LESSON_TITLE = "Beginner Level Lesson";
	private static final int INITIAL_VOCABULARY_LEVEL = 1;

	@InjectMocks
	VocabularyServiceImpl vocabularyServiceImpl;

	@Mock
	LessonRepository lessonRepository;

	@Mock
	VocabularyLevelRepository vocabularyLevelRepository;

	@Mock
	VocabularyCategoryRepository vocabularyCategoryRepository;

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

	private List<VocabularyCategory> createInProgressCategories() {
		VocabularyCategory vocabularyCategoryAntonyms = new VocabularyCategory();
		vocabularyCategoryAntonyms.setCatagoryType(VocabularyCategoryType.ANTONYMS);
		vocabularyCategoryAntonyms.setStatus(VocabularyStatus.IN_PROGRESS);

		VocabularyCategory vocabularyCategoryTranslations = new VocabularyCategory();
		vocabularyCategoryTranslations.setCatagoryType(VocabularyCategoryType.TRANSLATIONS);
		vocabularyCategoryTranslations.setStatus(VocabularyStatus.IN_PROGRESS);

		VocabularyCategory vocabularyCategoryPictures = new VocabularyCategory();
		vocabularyCategoryPictures.setCatagoryType(VocabularyCategoryType.PICTURES);
		vocabularyCategoryPictures.setStatus(VocabularyStatus.IN_PROGRESS);

		List<VocabularyCategory> categories = new ArrayList<>();
		categories.add(vocabularyCategoryAntonyms);
		categories.add(vocabularyCategoryTranslations);
		categories.add(vocabularyCategoryPictures);

		return categories;
	}

	private void assertOnlySpecificCategoryIsUpdated(User user, VocabularyCategoryType vocabularyCategoryType) {
		user.getVocabularyLevel().getCategories().stream()
				.filter(vocabularyCategory -> vocabularyCategory.getCatagoryType().equals(vocabularyCategoryType))//
				.forEach(vocabularyCategory -> assertEquals(VocabularyStatus.FINISHED, vocabularyCategory.getStatus()));

		user.getVocabularyLevel().getCategories().stream()
				.filter(vocabularyCategory -> !vocabularyCategory.getCatagoryType().equals(vocabularyCategoryType))//
				.forEach(vocabularyCategory -> assertEquals(VocabularyStatus.IN_PROGRESS,
						vocabularyCategory.getStatus()));

		assertEquals(INITIAL_VOCABULARY_LEVEL, user.getVocabularyLevel().getLevel());
	}

	private void assertAllCategoriesAreInProgressAndLevelIsUpdated(User user) {
		user.getVocabularyLevel().getCategories().stream()//
				.forEach(vocabularyCategory -> assertEquals(VocabularyStatus.IN_PROGRESS,
						vocabularyCategory.getStatus()));

		assertEquals(NEXT_LESSON_NUMBER, user.getVocabularyLevel().getLevel());
	}

	@Test
	public void createVocabularyLevel() {
		Lesson lesson = createLesson();
		VocabularyLevel expectedResult = new VocabularyLevel();
		expectedResult.setLesson(lesson);
		expectedResult.setLevel(lesson.getLessonNumber());

		when(lessonRepository.findByLevelAndLessonNumber(MainLevel.BEGINNER, LESSON_NUMBER))//
				.thenReturn(lesson);

		VocabularyLevel actualResult = vocabularyServiceImpl.createVocabularyLevel(MainLevel.BEGINNER);
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void categoryUpdateChangeOnlyAntonymsCategoryStatus() {
		HttpSession session = new MockHttpSession();
		session.setAttribute("email", EMAIL);

		User user = createUser();
		List<VocabularyCategory> categories = createInProgressCategories();
		user.getVocabularyLevel().setCategories(categories);

		when(userRepository.findByEmail(EMAIL)).thenReturn(user);

		vocabularyServiceImpl.categoryUpdate(session, VocabularyCategoryType.ANTONYMS.toString());
		assertOnlySpecificCategoryIsUpdated(user, VocabularyCategoryType.ANTONYMS);
	}

	@Test
	public void categoryUpdateChangeOnlyTranslationsCategoryStatus() {
		HttpSession session = new MockHttpSession();
		session.setAttribute("email", EMAIL);

		User user = createUser();
		List<VocabularyCategory> categories = createInProgressCategories();
		user.getVocabularyLevel().setCategories(categories);

		when(userRepository.findByEmail(EMAIL)).thenReturn(user);

		vocabularyServiceImpl.categoryUpdate(session, VocabularyCategoryType.TRANSLATIONS.toString());
		assertOnlySpecificCategoryIsUpdated(user, VocabularyCategoryType.TRANSLATIONS);
	}

	@Test
	public void categoryUpdateChangeOnlyPicturesCategoryStatus() {
		HttpSession session = new MockHttpSession();
		session.setAttribute("email", EMAIL);

		User user = createUser();
		List<VocabularyCategory> categories = createInProgressCategories();
		user.getVocabularyLevel().setCategories(categories);

		when(userRepository.findByEmail(EMAIL)).thenReturn(user);

		vocabularyServiceImpl.categoryUpdate(session, VocabularyCategoryType.PICTURES.toString());
		assertOnlySpecificCategoryIsUpdated(user, VocabularyCategoryType.PICTURES);
	}

	@Test
	public void categoryUpdateChangeStatusAntonymsAndUpdateEverything() {
		HttpSession session = new MockHttpSession();
		session.setAttribute("email", EMAIL);

		User user = createUser();
		List<VocabularyCategory> categories = createInProgressCategories();
		categories.stream()//
				.filter(vocabularyCategory -> !vocabularyCategory.getCatagoryType()
						.equals(VocabularyCategoryType.ANTONYMS))//
				.forEach(vocabularyCategory -> vocabularyCategory.setStatus(VocabularyStatus.FINISHED));
		user.getVocabularyLevel().setCategories(categories);

		Lesson nextLesson = new Lesson();
		nextLesson.setLessonNumber(NEXT_LESSON_NUMBER);
		nextLesson.setLevel(MainLevel.BEGINNER);
		String nextLessonTitle = "nextLessonTitle";
		nextLesson.setTitle(nextLessonTitle);

		when(userRepository.findByEmail(EMAIL)).thenReturn(user);
		when(lessonRepository.findByLevelAndLessonNumber(MainLevel.BEGINNER, NEXT_LESSON_NUMBER))//
				.thenReturn(nextLesson);

		vocabularyServiceImpl.categoryUpdate(session, VocabularyCategoryType.ANTONYMS.toString());
		assertAllCategoriesAreInProgressAndLevelIsUpdated(user);
	}

	@Test
	public void categoryUpdateChangeStatusTranslationsAndUpdateEverything() {
		HttpSession session = new MockHttpSession();
		session.setAttribute("email", EMAIL);

		User user = createUser();
		List<VocabularyCategory> categories = createInProgressCategories();
		categories.stream()//
				.filter(vocabularyCategory -> !vocabularyCategory.getCatagoryType()
						.equals(VocabularyCategoryType.TRANSLATIONS))//
				.forEach(vocabularyCategory -> vocabularyCategory.setStatus(VocabularyStatus.FINISHED));
		user.getVocabularyLevel().setCategories(categories);

		Lesson nextLesson = new Lesson();
		nextLesson.setLessonNumber(NEXT_LESSON_NUMBER);
		nextLesson.setLevel(MainLevel.BEGINNER);
		String nextLessonTitle = "nextLessonTitle";
		nextLesson.setTitle(nextLessonTitle);

		when(userRepository.findByEmail(EMAIL)).thenReturn(user);
		when(lessonRepository.findByLevelAndLessonNumber(MainLevel.BEGINNER, NEXT_LESSON_NUMBER))//
				.thenReturn(nextLesson);

		vocabularyServiceImpl.categoryUpdate(session, VocabularyCategoryType.TRANSLATIONS.toString());
		assertAllCategoriesAreInProgressAndLevelIsUpdated(user);
	}

	@Test
	public void categoryUpdateChangeStatusPictureAndUpdateEverything() {
		HttpSession session = new MockHttpSession();
		session.setAttribute("email", EMAIL);

		User user = createUser();
		List<VocabularyCategory> categories = createInProgressCategories();
		categories.stream()//
				.filter(vocabularyCategory -> !vocabularyCategory.getCatagoryType()
						.equals(VocabularyCategoryType.PICTURES))//
				.forEach(vocabularyCategory -> vocabularyCategory.setStatus(VocabularyStatus.FINISHED));
		user.getVocabularyLevel().setCategories(categories);

		Lesson nextLesson = new Lesson();
		nextLesson.setLessonNumber(NEXT_LESSON_NUMBER);
		nextLesson.setLevel(MainLevel.BEGINNER);
		String nextLessonTitle = "nextLessonTitle";
		nextLesson.setTitle(nextLessonTitle);

		when(userRepository.findByEmail(EMAIL)).thenReturn(user);
		when(lessonRepository.findByLevelAndLessonNumber(MainLevel.BEGINNER, NEXT_LESSON_NUMBER))//
				.thenReturn(nextLesson);

		vocabularyServiceImpl.categoryUpdate(session, VocabularyCategoryType.PICTURES.toString());
		assertAllCategoriesAreInProgressAndLevelIsUpdated(user);
	}
}
