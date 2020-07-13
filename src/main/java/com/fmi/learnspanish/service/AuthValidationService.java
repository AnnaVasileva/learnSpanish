package com.fmi.learnspanish.service;

import com.fmi.learnspanish.web.exeptionhandling.InvalidUserException;
import com.fmi.learnspanish.web.resource.RegisterUserResource;

public interface AuthValidationService {

  boolean isValid(RegisterUserResource registerUserResource) throws InvalidUserException;
}
