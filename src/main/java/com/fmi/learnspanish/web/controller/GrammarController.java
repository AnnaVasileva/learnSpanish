// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fmi.learnspanish.service.GrammarService;

@Controller
@RequestMapping("/grammar")
public class GrammarController {

	@Autowired
	private GrammarService grammarService;

	@GetMapping("/lesson-{lessonNumber}")
	public ModelAndView getGrammarLesson(@PathVariable int lessonNumber, ModelAndView modelAndView,
			HttpSession session) {
		grammarService.setLessonGrammar(session, lessonNumber);
		modelAndView.setViewName("grammar-lessons/grammar-lesson-content.html");
		return modelAndView;
	}

	@PostMapping("/grammarUp")
	public ModelAndView levelUp(HttpSession session) {
		grammarService.grammarUp(session);
		return new ModelAndView("redirect:/learn/grammar/" + session.getAttribute("email"));
	}

}
