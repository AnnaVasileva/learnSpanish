package com.fmi.learnspanish.service;

import com.fmi.learnspanish.domain.User;
import com.fmi.learnspanish.web.rest.resource.RegisterUserResource;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  User createUser(RegisterUserResource registerUserResource);

  // User checkUserExistence(String email);

}
