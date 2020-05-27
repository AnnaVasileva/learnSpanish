package com.fmi.learnspanish.service;

import com.fmi.learnspanish.domain.User;

import javax.servlet.http.HttpSession;

public interface SessionService {

  void setSessionAttributes(HttpSession session, User user);

}
