// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.fmi.learnspanish.domain.MainLevel;
import com.fmi.learnspanish.domain.PracticeLevel;
import com.fmi.learnspanish.web.resource.QuestionResource;

public interface PracticeService {

	PracticeLevel createPracticeLevel(MainLevel level);

	List<QuestionResource> getQuestions(HttpSession session, int lessonNumber);

	void practiceUp(HttpSession session, int lessonNumber);
}
