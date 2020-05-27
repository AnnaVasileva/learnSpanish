package com.fmi.learnspanish.web.rest.controller;

import com.fmi.learnspanish.domain.User;
import com.fmi.learnspanish.service.AuthService;
import com.fmi.learnspanish.service.AuthValidationService;
import com.fmi.learnspanish.service.SessionService;
import com.fmi.learnspanish.service.UserService;
import com.fmi.learnspanish.web.rest.resource.RegisterUserResource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/users")
public class AuthController {

  @Autowired
  private AuthValidationService authValidationService;

  @Autowired
  private AuthService authService;

  @Autowired
  private SessionService sessionService;

  @Autowired
  private UserService userService;

  @GetMapping("/login")
  public ModelAndView getLoginForm(ModelAndView modelAndView) {
    modelAndView.setViewName("auth/login.html");
    return modelAndView;
  }

  @GetMapping("/register")
  public ModelAndView getRegisterForm(ModelAndView modelAndView) {
    modelAndView.setViewName("auth/register.html");
    return modelAndView;
  }

  @PostMapping("/register")
  public ModelAndView register(@ModelAttribute RegisterUserResource model) throws Exception {
    System.out.println("in register method");
    if (authValidationService.isValid(model)) {
      User user = userService.createUser(model);
    }

    // authService.register(user);

    return new ModelAndView("redirect:/users/login");
  }

  // @PostMapping("/register")
  // public ModelAndView register(@ModelAttribute RegisterUserResource model) throws Exception {
  // authValidationService.isValid(model);
  //
  // userService.register(model);
  //
  // return new ModelAndView("redirect:/users/login");
  // }

  // @PostMapping("/login")
  // public ModelAndView login(@ModelAttribute LoginUserResource model, HttpSession session) {
  // try {
  // User user = authService.login(model);
  // sessionService.setSessionAttributes(session, user);
  // return new ModelAndView("redirect:/home");
  //
  // } catch (Exception ex) {
  // return new ModelAndView("redirect:/users/login");
  // }
  // }
}
