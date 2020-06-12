package com.fmi.learnspanish.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.fmi.learnspanish.domain.User;
import com.fmi.learnspanish.web.rest.resource.RegisterUserResource;
import com.fmi.learnspanish.web.rest.resource.UserStatisticsResource;

public interface UserService extends UserDetailsService {

  User createUser(RegisterUserResource registerUserResource);

  // User checkUserExistence(String email);
  
  List<UserStatisticsResource> getUsersStatistics();	

}
