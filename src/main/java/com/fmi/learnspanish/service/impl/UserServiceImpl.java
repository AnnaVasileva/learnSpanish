package com.fmi.learnspanish.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fmi.learnspanish.domain.GrammarLevel;
import com.fmi.learnspanish.domain.Role;
import com.fmi.learnspanish.domain.User;
import com.fmi.learnspanish.domain.VocabularyCategory;
import com.fmi.learnspanish.domain.VocabularyLevel;
import com.fmi.learnspanish.repository.RoleRepository;
import com.fmi.learnspanish.repository.UserRepository;
import com.fmi.learnspanish.service.GrammarService;
import com.fmi.learnspanish.service.UserService;
import com.fmi.learnspanish.service.VocabularyService;
import com.fmi.learnspanish.web.rest.resource.RegisterUserResource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private GrammarService grammarService;

	@Autowired
	private VocabularyService vocabularyService;

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
		System.out.println("==========createUser==============");
		User user = new User();
		user.setUsername(registerUserResource.getUsername());
		System.out.printf("name --> %s%n", user.getUsername());
		user.setEmail(registerUserResource.getEmail());
		System.out.printf("getEmail --> %s%n", user.getEmail());
		user.setPassword(bCryptPasswordEncoder.encode(registerUserResource.getPassword()));

		GrammarLevel grammarLevel = grammarService.createGrammarLevel();
		user.setGrammarLevel(grammarLevel);
		System.out.printf("grammarLevel --> %s%n", user.getGrammarLevel());

		VocabularyLevel vocabularyLevel = vocabularyService.createVocabularyLevel();
		user.setVocabularyLevel(vocabularyLevel);
		System.out.printf("vocabularyLevel --> %s%n", user.getVocabularyLevel());
		Collection<VocabularyCategory> cat = user.getVocabularyLevel().getCategories();
		cat.forEach(c -> System.out.printf("category --> %s%n", c.getId()));

		Role role = roleRepository.findByAuthority("USER");
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		System.out.printf("roles ---> ", role);
		user.setAuthorities(roles);
		user.getAuthorities().forEach(auth -> System.out.printf("user aut --> %s%n", auth));
		
		System.out.println("successful created user");
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
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);

		Set<GrantedAuthority> authorities = new HashSet<>(user.getAuthorities());
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				authorities);
	}
}
