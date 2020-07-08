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
    if (!arePasswordsMatching(registerUserResource.getPassword(), registerUserResource.getConfirmPassword())
        || isAccountTaken(registerUserResource.getEmail())) {
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
