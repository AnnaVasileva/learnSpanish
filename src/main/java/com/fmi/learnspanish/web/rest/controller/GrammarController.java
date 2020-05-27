package com.fmi.learnspanish.web.rest.controller;

import com.fmi.learnspanish.service.GrammarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/grammar/lesson")
public class GrammarController {

  @Autowired
  private GrammarService grammarService;

  @GetMapping("/grammar-lesson-{number}")
  public ModelAndView getGrammarLesson(@PathVariable int number, ModelAndView modelAndView) {
    modelAndView.setViewName("grammar-lessons/grammar-lesson-" + number + ".html");
    return modelAndView;

  }

  @PostMapping("/grammarUp")
  public ModelAndView levelUp(HttpSession session) {
    int newLevel = grammarService.grammarUp((String) session.getAttribute("email"));
    session.setAttribute("grammarLevel", newLevel);
    return new ModelAndView("redirect:/learn/grammar/" + session.getAttribute("email"));
  }

}
