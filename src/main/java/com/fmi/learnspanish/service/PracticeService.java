package com.fmi.learnspanish.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.fmi.learnspanish.domain.PracticeLevel;
import com.fmi.learnspanish.web.resource.QuestionResource;

public interface PracticeService {

	PracticeLevel createPracticeLevel();

	List<QuestionResource> getQuestions(int lessonNumber);

	void practiceUp(HttpSession session, int lessonNumber);
}
