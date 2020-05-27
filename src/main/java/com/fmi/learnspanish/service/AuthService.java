package com.fmi.learnspanish.service;

import com.fmi.learnspanish.domain.User;
import com.fmi.learnspanish.web.rest.resource.LoginUserResource;

public interface AuthService {

  void register(User user) throws Exception;

  User login(LoginUserResource registerUserResource) throws Exception;

}
