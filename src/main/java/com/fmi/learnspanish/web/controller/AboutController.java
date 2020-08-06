// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AboutController {

  @GetMapping("/about")
  public ModelAndView getAbout(ModelAndView modelAndView) {
    modelAndView.setViewName("about/about.html");
    return modelAndView;
  }
}
