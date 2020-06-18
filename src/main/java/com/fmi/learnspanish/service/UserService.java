package com.fmi.learnspanish.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.fmi.learnspanish.domain.User;
import com.fmi.learnspanish.web.rest.resource.RegisterUserResource;

public interface UserService extends UserDetailsService {

  User createUser(RegisterUserResource registerUserResource);

  // User checkUserExistence(String email);
  
}
