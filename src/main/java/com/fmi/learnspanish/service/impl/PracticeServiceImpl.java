// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fmi.learnspanish.domain.Lesson;
import com.fmi.learnspanish.domain.MainLevel;
import com.fmi.learnspanish.domain.PracticeLevel;
import com.fmi.learnspanish.domain.Question;
import com.fmi.learnspanish.domain.User;
import com.fmi.learnspanish.repository.LessonRepository;
import com.fmi.learnspanish.repository.PracticeLevelRepository;
import com.fmi.learnspanish.repository.QuestionRepository;
import com.fmi.learnspanish.repository.UserRepository;
import com.fmi.learnspanish.service.PracticeService;
import com.fmi.learnspanish.service.SessionService;
import com.fmi.learnspanish.web.resource.QuestionResource;

@Service
public class PracticeServiceImpl implements PracticeService {

	private static final int DEFAULT_LESSON = 1;

	@Autowired
	private LessonRepository lessonRepository;

	@Autowired
	private PracticeLevelRepository practiceLevelRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SessionService sessionService;

	@Override
	public PracticeLevel createPracticeLevel(MainLevel level) {
		Lesson lesson = lessonRepository.findByLevelAndLessonNumber(level, DEFAULT_LESSON);
		PracticeLevel practiceLevel = new PracticeLevel();
		practiceLevel.setLesson(lesson);
		practiceLevel.setLevel(lesson.getLessonNumber());
		practiceLevelRepository.save(practiceLevel);
		return practiceLevel;
	}

	@Override
	public List<QuestionResource> getQuestions(HttpSession session, int lessonNumber) {
		MainLevel level = (MainLevel) session.getAttribute("level");
		Lesson lesson = lessonRepository.findByLevelAndLessonNumber(level, lessonNumber);
		List<Question> questions = questionRepository.findAllByLessonId(lesson.getId());

		List<QuestionResource> questionResourceList = new ArrayList<>();
		questions.stream().forEach(question -> {
			QuestionResource questionResource = new QuestionResource();
			questionResource.setText(question.getContent());

			Set<String> choices = new HashSet<>();
			choices.add(question.getCorrectAnswer());
			choices.add(question.getWrongOption1());
			choices.add(question.getWrongOption2());
			choices.add(question.getWrongOption3());
			questionResource.setChoices(choices);

			questionResource.setAnswer(question.getCorrectAnswer());

			questionResourceList.add(questionResource);
		});

		return questionResourceList;
	}

	@Override
	public void practiceUp(HttpSession session, int lessonNumber) {
		User user = userRepository.findByEmail((String) session.getAttribute("email"));
		MainLevel level = user.getLevel();
		
		if (user.getPracticeLevel().getLevel() == lessonNumber) {

			int nextLessonNumber = user.getPracticeLevel().getLevel() + 1;
			Lesson nextLesson = lessonRepository.findByLevelAndLessonNumber(level, nextLessonNumber);

			if (Objects.nonNull(nextLesson)) {
				user.getPracticeLevel().setLevel(nextLessonNumber);
				user.getPracticeLevel().setLesson(nextLesson);
			}

			userRepository.saveAndFlush(user);
			sessionService.setSessionAttributes(session, user);
		}
	}

}
