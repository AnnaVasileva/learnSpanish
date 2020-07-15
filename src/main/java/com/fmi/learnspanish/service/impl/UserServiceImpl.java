package com.fmi.learnspanish.service.impl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fmi.learnspanish.domain.GrammarLevel;
import com.fmi.learnspanish.domain.MainLevel;
import com.fmi.learnspanish.domain.PracticeLevel;
import com.fmi.learnspanish.domain.Role;
import com.fmi.learnspanish.domain.User;
import com.fmi.learnspanish.domain.VocabularyLevel;
import com.fmi.learnspanish.repository.RoleRepository;
import com.fmi.learnspanish.repository.UserRepository;
import com.fmi.learnspanish.service.GrammarService;
import com.fmi.learnspanish.service.PracticeService;
import com.fmi.learnspanish.service.UserService;
import com.fmi.learnspanish.service.VocabularyService;
import com.fmi.learnspanish.web.resource.RegisterUserResource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private GrammarService grammarService;

	@Autowired
	private VocabularyService vocabularyService;

	@Autowired
	private PracticeService practiceService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User createUser(RegisterUserResource registerUserResource) {
		if (registerUserResource == null) {
			return null;
		}

		User user = new User();
		user.setUsername(registerUserResource.getUsername());
		user.setEmail(registerUserResource.getEmail());
		user.setPassword(bCryptPasswordEncoder.encode(registerUserResource.getPassword()));

		MainLevel level = null;
		if (Objects.isNull(registerUserResource.getLevel())
				|| registerUserResource.getLevel().equalsIgnoreCase(MainLevel.BEGINNER.toString())) {
			level = MainLevel.BEGINNER;
			user.setLevel(level);
		} else {
			level = MainLevel.INTERMIDIATE;
			user.setLevel(level);
		}

		GrammarLevel grammarLevel = grammarService.createGrammarLevel(level);
		user.setGrammarLevel(grammarLevel);

		VocabularyLevel vocabularyLevel = vocabularyService.createVocabularyLevel(level);
		user.setVocabularyLevel(vocabularyLevel);

		PracticeLevel practiceLevel = practiceService.createPracticeLevel(level);
		user.setPracticeLevel(practiceLevel);

		Role role = roleRepository.findByAuthority("USER");
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setAuthorities(roles);

		userRepository.saveAndFlush(user);
		log.info("User {} was successfully created.", user.getUsername());
		return user;
	}

	// @Override
	// public User checkUserExistence(String email) {
	// User user = userRepository.findByEmail(email);
	// if (Objects.isNull(user)) {
	// throw new UserNotFoundException("Sorry, user not found.");
	// }
	// return user;
	//
	// }

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username);

		if (Objects.isNull(user)) {
			throw new UsernameNotFoundException("Sorry, user " + username + " is not found.");
		}

		Set<GrantedAuthority> authorities = new HashSet<>(user.getAuthorities());
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				authorities);
	}
}
