package com.fmi.learnspanish.web.rest.controller;

import com.fmi.learnspanish.service.AuthValidationService;
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
    if (authValidationService.isValid(model)) {
      userService.createUser(model);
    }
    return new ModelAndView("redirect:/users/login");
  }

}
