// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.fmi.learnspanish.domain.User;
import com.fmi.learnspanish.web.resource.RegisterUserResource;

public interface UserService extends UserDetailsService {

  User createUser(RegisterUserResource registerUserResource);
  
}
