// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fmi.learnspanish.repository.UserRepository;
import com.fmi.learnspanish.service.AuthValidationService;
import com.fmi.learnspanish.web.exeptionhandling.InvalidUserException;
import com.fmi.learnspanish.web.resource.RegisterUserResource;

@Service
public class AuthValidationServiceImpl implements AuthValidationService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean isValid(RegisterUserResource registerUserResource) throws InvalidUserException {
		String password = registerUserResource.getPassword();
		String confirmPassword = registerUserResource.getConfirmPassword();
		String email = registerUserResource.getEmail();

		if (!arePasswordsMatching(password, confirmPassword) || isAccountTaken(email)) {
			throw new InvalidUserException("Invalid user. Please, try again.");
		}
		
		return true;
	}

	private boolean arePasswordsMatching(String password, String confirmPassword) {
		return password.equals(confirmPassword);
	}

	private boolean isAccountTaken(String email) {
		return userRepository.existsByEmail(email);
	}

}
