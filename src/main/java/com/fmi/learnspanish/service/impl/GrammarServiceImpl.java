package com.fmi.learnspanish.service.impl;

import com.fmi.learnspanish.domain.GrammarLevel;
import com.fmi.learnspanish.domain.Lesson;
import com.fmi.learnspanish.domain.User;
import com.fmi.learnspanish.repository.GrammarLevelRepository;
import com.fmi.learnspanish.repository.LessonRepository;
import com.fmi.learnspanish.repository.UserRepository;
import com.fmi.learnspanish.service.GrammarService;
import com.fmi.learnspanish.service.SessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import javax.servlet.http.HttpSession;

@Service
public class GrammarServiceImpl implements GrammarService {

  private static final int DEFAULT_LESSON = 1;

  @Autowired
  private LessonRepository lessonRepository;

  @Autowired
  private GrammarLevelRepository grammarLevelRepository;

  @Autowired
  private SessionService sessionService;

  @Autowired
  private UserRepository userRepository;

  @Override
  public GrammarLevel createGrammarLevel() {
    Lesson lesson = lessonRepository.findByLessonNumber(DEFAULT_LESSON);
    GrammarLevel grammarLevel = new GrammarLevel();
    grammarLevel.setLesson(lesson);
    grammarLevel.setLevel(lesson.getLessonNumber());
    grammarLevelRepository.save(grammarLevel);
    return grammarLevel;
  }

  @Override
  public void grammarUp(HttpSession session) {
    User user = userRepository.findByEmail((String) session.getAttribute("email"));

    int nextLessonNumber = user.getGrammarLevel().getLevel() + 1;
    Lesson nextLesson = lessonRepository.findByLessonNumber(nextLessonNumber);

    if (Objects.nonNull(nextLesson)) {
      user.getGrammarLevel().setLevel(nextLessonNumber);
      user.getGrammarLevel().setLesson(nextLesson);
    }

    userRepository.saveAndFlush(user);
    sessionService.setSessionAttributes(session, user);
  }

}