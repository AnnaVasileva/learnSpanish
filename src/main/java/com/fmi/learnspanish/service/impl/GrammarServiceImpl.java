package com.fmi.learnspanish.service.impl;

import com.fmi.learnspanish.domain.GrammarLevel;
import com.fmi.learnspanish.domain.Lesson;
import com.fmi.learnspanish.domain.User;
import com.fmi.learnspanish.repository.GrammarLevelRepository;
import com.fmi.learnspanish.repository.LessonRepository;
import com.fmi.learnspanish.repository.UserRepository;
import com.fmi.learnspanish.service.GrammarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GrammarServiceImpl implements GrammarService {

  private static final int DEFAULT_LESSON = 1;

  @Autowired
  private LessonRepository lessonRepository;

  @Autowired
  private GrammarLevelRepository grammarLevelRepository;

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
  public int grammarUp(String email) {
    User user = userRepository.findByEmail(email);

    int nextLessonNumber = user.getGrammarLevel().getLevel() + 1;
    Lesson nextLesson = lessonRepository.findByLessonNumber(nextLessonNumber);

    if (Objects.nonNull(nextLesson)) {
      user.getGrammarLevel().setLevel(nextLessonNumber);
      user.getGrammarLevel().setLesson(nextLesson);
    }

    userRepository.saveAndFlush(user);
    return user.getGrammarLevel().getLevel();
  }

}