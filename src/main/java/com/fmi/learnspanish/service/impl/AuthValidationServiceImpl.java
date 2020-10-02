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

	private static final int MIN_PASSWORD_LENGHT = 8;

	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean isValid(RegisterUserResource registerUserResource) throws InvalidUserException {
		String password = registerUserResource.getPassword();
		String confirmPassword = registerUserResource.getConfirmPassword();
		String email = registerUserResource.getEmail();

		if (!isMinLenghtCovered(password) || !arePasswordsMatching(password, confirmPassword)
				|| isAccountTaken(email)) {
			throw new InvalidUserException("Somthing went wrong. Please, try again.");
		}

		return true;
	}

	private boolean isMinLenghtCovered(String password) {
		return password.length() >= MIN_PASSWORD_LENGHT;
	}

	private boolean arePasswordsMatching(String password, String confirmPassword) {
		return password.equals(confirmPassword);
	}

	private boolean isAccountTaken(String email) {
		return userRepository.existsByEmail(email);
	}

}
