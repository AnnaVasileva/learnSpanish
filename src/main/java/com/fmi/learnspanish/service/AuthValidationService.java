package com.fmi.learnspanish.service;

import com.fmi.learnspanish.web.rest.resource.RegisterUserResource;

public interface AuthValidationService {

  boolean isValid(RegisterUserResource registerUserResource) throws Exception;
}
