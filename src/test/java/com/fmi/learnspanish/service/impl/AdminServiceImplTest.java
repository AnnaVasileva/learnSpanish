package com.fmi.learnspanish.service.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fmi.learnspanish.domain.GrammarLevel;
import com.fmi.learnspanish.domain.Lesson;
import com.fmi.learnspanish.domain.MainLevel;
import com.fmi.learnspanish.domain.PracticeLevel;
import com.fmi.learnspanish.domain.Role;
import com.fmi.learnspanish.domain.User;
import com.fmi.learnspanish.domain.VocabularyLevel;
import com.fmi.learnspanish.repository.RoleRepository;
import com.fmi.learnspanish.repository.UserRepository;
import com.fmi.learnspanish.web.exeptionhandling.AdminAlreadyExistsException;
import com.fmi.learnspanish.web.exeptionhandling.StatisticsNotFoundException;
import com.fmi.learnspanish.web.exeptionhandling.UserNotFoundException;
import com.fmi.learnspanish.web.resource.MakeAdminResource;
import com.fmi.learnspanish.web.resource.UserStatisticsResource;

public class AdminServiceImplTest {

	private static final String USERNAME = "testUsername";
	private static final String EMAIL = "testEmail";
	private static final String LESSON_TITLE = "Beginner Level Lesson";
	private static final String CAPITALIZED_MAIN_LEVEL = "Beginner";
	private static final String ROLE_ADMIN = "ADMIN";

	@InjectMocks
	AdminServiceImpl adminServiceImpl;

	@Mock
	UserRepository userRepository;

	@Mock
	RoleRepository roleRepository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	private User createUser() {
		User user = new User();
		user.setUsername(USERNAME);
		user.setEmail(EMAIL);
		user.setLevel(MainLevel.BEGINNER);

		Lesson lesson = new Lesson();
		lesson.setLessonNumber(1);
		lesson.setLevel(MainLevel.BEGINNER);
		lesson.setTitle(LESSON_TITLE);

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

	private MakeAdminResource createMakeAdminResource() {
		MakeAdminResource makeAdminResource = new MakeAdminResource();
		makeAdminResource.setUsername(USERNAME);
		makeAdminResource.setEmail(EMAIL);

		return makeAdminResource;
	}

	@Test
	public void getUsersStatisticsOk() throws StatisticsNotFoundException {
		User user = createUser();
		List<User> usersList = Collections.singletonList(user);

		UserStatisticsResource userStatisticsResource = new UserStatisticsResource();
		userStatisticsResource.setUsername(USERNAME);
		userStatisticsResource.setMainLevel(CAPITALIZED_MAIN_LEVEL);
		userStatisticsResource.setGrammarLevel(LESSON_TITLE);
		userStatisticsResource.setVocabularyLevel(LESSON_TITLE);
		userStatisticsResource.setPracticeLevel(LESSON_TITLE);
		List<UserStatisticsResource> expextedResult = Collections.singletonList(userStatisticsResource);

		when(userRepository.findAllByOrderByUsername()).thenReturn(usersList);

		List<UserStatisticsResource> actualResult = adminServiceImpl.getUsersStatistics();
		assertEquals(expextedResult, actualResult);
	}

	@Test
	public void getUsersStatisticsThrowStatisticesNotFoundException() {
		when(userRepository.findAllByOrderByUsername()).thenReturn(null);
		assertThatThrownBy(() -> adminServiceImpl.getUsersStatistics()).isInstanceOf(StatisticsNotFoundException.class);
	}

	@Test
	public void makeAdminOk() throws AdminAlreadyExistsException, UserNotFoundException {
		MakeAdminResource makeAdminResource = createMakeAdminResource();

		Role adminRole = new Role();
		adminRole.setAuthority(ROLE_ADMIN);

		User user = createUser();

		when(userRepository.findByUsernameAndEmail(makeAdminResource.getUsername(), makeAdminResource.getEmail()))
				.thenReturn(user);
		when(roleRepository.findByAuthority(ROLE_ADMIN)).thenReturn(adminRole);

		adminServiceImpl.makeAdmin(makeAdminResource);
		verify(userRepository, times(1)).saveAndFlush(user);
	}

	@Test
	public void makeAdminThrowAdminAlreadyExistsException() {
		MakeAdminResource makeAdminResource = createMakeAdminResource();

		Role adminRole = new Role();
		adminRole.setAuthority(ROLE_ADMIN);
		Set<Role> authorities = new HashSet<>();
		authorities.add(adminRole);

		User user = createUser();
		user.setAuthorities(authorities);

		when(userRepository.findByUsernameAndEmail(makeAdminResource.getUsername(), makeAdminResource.getEmail()))
				.thenReturn(user);
		when(roleRepository.findByAuthority(ROLE_ADMIN)).thenReturn(adminRole);

		assertThatThrownBy(() -> adminServiceImpl.makeAdmin(makeAdminResource))
				.isInstanceOf(AdminAlreadyExistsException.class);
	}

	@Test
	public void makeAdminThrowUserNotFoundException() {
		MakeAdminResource makeAdminResource = createMakeAdminResource();

		when(userRepository.findByUsernameAndEmail(makeAdminResource.getUsername(), makeAdminResource.getEmail()))
				.thenReturn(null);

		assertThatThrownBy(() -> adminServiceImpl.makeAdmin(makeAdminResource))
				.isInstanceOf(UserNotFoundException.class);
	}

}
