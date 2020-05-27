package com.fmi.learnspanish.web.rest.controller;

import com.fmi.learnspanish.service.SessionService;
import com.fmi.learnspanish.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private SessionService sessionService;

  @GetMapping("/profile/{email}")
  public ModelAndView getUserProfile(@PathVariable String email, ModelAndView modelAndView, HttpSession session) {
    // User user = userService.checkUserExistence(email);
    // sessionService.setSessionAttributes(session, user);
    modelAndView.setViewName("user/profile.html");
    return modelAndView;
  }

}
