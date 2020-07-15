package com.fmi.learnspanish.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fmi.learnspanish.service.VocabularyService;
import com.fmi.learnspanish.service.WordService;
import com.fmi.learnspanish.web.resource.FlashcardResource;

@Controller
@RequestMapping("/vocabulary")
public class VocabularyController {

  private static final String REDIRECT_LEARN_VOCABULARY = "redirect:/learn/vocabulary/";
  private static final String EMAIL = "email";

  @Autowired
  private VocabularyService vocabularyService;

  @Autowired
  private WordService wordService;

  @GetMapping("/lesson-{lessonNumber}")
  public ModelAndView getVocabularyLessonDecks(@PathVariable int lessonNumber, ModelAndView modelAndView) {
	  modelAndView.setViewName("learn/vocabulary-menu.html");
    modelAndView.addObject("currentLesson", lessonNumber);
    return modelAndView;
  }

  @GetMapping("/{categoryType}-deck-{lessonNumber}")
  public ModelAndView getDeck(@PathVariable String categoryType, @PathVariable int lessonNumber,
		  HttpSession session, ModelAndView modelAndView) {
    List<FlashcardResource> deck = wordService.getCards(session, categoryType, lessonNumber);

    modelAndView.setViewName("learnCategories/" + categoryType + ".html");
    modelAndView.addObject("deck", deck);
    modelAndView.addObject("currentLesson", lessonNumber);
    return modelAndView;
  }

  @PostMapping("/{category}Up")
  public ModelAndView categoryUp(@PathVariable String category, HttpSession session) {
    vocabularyService.categoryUpdate(session, category);
    return new ModelAndView(REDIRECT_LEARN_VOCABULARY + session.getAttribute(EMAIL));
  }
}