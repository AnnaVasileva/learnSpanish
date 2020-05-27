package com.fmi.learnspanish.web.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class AboutController {

  @GetMapping("/about")
  public ModelAndView getAbout(ModelAndView modelAndView) {
    modelAndView.setViewName("about/about.html");
    return modelAndView;
  }
}
