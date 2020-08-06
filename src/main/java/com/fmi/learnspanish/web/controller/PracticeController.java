// All Rights Reserved, Copyright © Anna Vasileva 2020.

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

import com.fmi.learnspanish.service.PracticeService;
import com.fmi.learnspanish.web.resource.QuestionResource;

@Controller
@RequestMapping("/practice")
public class PracticeController {

	@Autowired
	private PracticeService practiceService;

	@GetMapping("/lesson-{lessonNumber}")
	public ModelAndView getQuesions(@PathVariable int lessonNumber, HttpSession session, ModelAndView modelAndView) {
		List<QuestionResource> questions = practiceService.getQuestions(session, lessonNumber);
		modelAndView.setViewName("practice-lessons/quiz.html");
		modelAndView.addObject("questions", questions);
		modelAndView.addObject("currentPracticeLessonNumber", lessonNumber);
		return modelAndView;
	}

	@PostMapping("/practiceUp-{lessonNumber}")
	public void practiceLevelUp(@PathVariable int lessonNumber, HttpSession session) {
		practiceService.practiceUp(session, lessonNumber);
	}

}
