package com.fmi.learnspanish.service.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fmi.learnspanish.domain.MainLevel;
import com.fmi.learnspanish.domain.Role;
import com.fmi.learnspanish.domain.User;
import com.fmi.learnspanish.repository.RoleRepository;
import com.fmi.learnspanish.repository.UserRepository;
import com.fmi.learnspanish.service.GrammarService;
import com.fmi.learnspanish.service.PracticeService;
import com.fmi.learnspanish.service.VocabularyService;
import com.fmi.learnspanish.web.resource.RegisterUserResource;

public class UserServiceImplTest {

	private static final String USERNAME = "testUsername";
	private static final String EMAIL = "testEmail";
	private static final String PASSWORD = "testPassword";
	private static final String ROLE_USER = "USER";

	@InjectMocks
	UserServiceImpl userServiceImpl;

	@Mock
	GrammarService grammarService;

	@Mock
	VocabularyService vocabularyService;

	@Mock
	PracticeService practiceService;

	@Mock
	UserRepository userRepository;

	@Mock
	RoleRepository roleRepository;

	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	private RegisterUserResource createRegisterUserResource() {
		RegisterUserResource registerUserResource = new RegisterUserResource();
		registerUserResource.setUsername(USERNAME);
		registerUserResource.setEmail(EMAIL);
		registerUserResource.setPassword(PASSWORD);
		return registerUserResource;
	}

	private User createUser() {
		User user = new User();
		user.setUsername(USERNAME);
		user.setEmail(EMAIL);
		user.setPassword(PASSWORD);
		return user;
	}

	@Test
	public void createUserBeginnerOk() {
		RegisterUserResource registerUserResource = createRegisterUserResource();
		registerUserResource.setLevel(MainLevel.BEGINNER.toString());

		User expectedResult = createUser();
		expectedResult.setLevel(MainLevel.BEGINNER);

		User actualResult = userServiceImpl.createUser(registerUserResource);

		assertEquals(expectedResult, actualResult);
		assertEquals(expectedResult.getLevel(), actualResult.getLevel());
	}

	@Test
	public void createUserIntermidiateOk() {
		RegisterUserResource registerUserResource = createRegisterUserResource();
		registerUserResource.setLevel(MainLevel.INTERMIDIATE.toString());

		User expectedResult = createUser();
		expectedResult.setLevel(MainLevel.INTERMIDIATE);

		User actualResult = userServiceImpl.createUser(registerUserResource);

		assertEquals(expectedResult, actualResult);
		assertEquals(expectedResult.getLevel(), actualResult.getLevel());
	}

	@Test
	public void createUserMainLevelNullOk() {
		RegisterUserResource registerUserResource = createRegisterUserResource();

		User expectedResult = createUser();
		expectedResult.setLevel(MainLevel.BEGINNER);

		User actualResult = userServiceImpl.createUser(registerUserResource);

		assertEquals(expectedResult, actualResult);
		assertEquals(expectedResult.getLevel(), actualResult.getLevel());
	}

	@Test
	public void createUserReturnNull() {
		assertNull(userServiceImpl.createUser(null));
	}

	@Test
	public void loadUserByUsernameOk() {
		User user = createUser();
		Role adminRole = new Role();
		adminRole.setAuthority(ROLE_USER);
		Set<Role> authorities = new HashSet<>();
		authorities.add(adminRole);
		user.setAuthorities(authorities);

		when(userRepository.findByUsername(USERNAME)).thenReturn(user);

		assertNotNull(userServiceImpl.loadUserByUsername(USERNAME));
	}

	@Test
	public void loadUserByUsernameThrowUsernameNotFoundException() {
		when(userRepository.findByUsername(USERNAME)).thenReturn(null);
		assertThatThrownBy(() -> userServiceImpl.loadUserByUsername(USERNAME))
				.isInstanceOf(UsernameNotFoundException.class);
	}

}
