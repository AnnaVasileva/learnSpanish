package com.fmi.learnspanish.service;

import com.fmi.learnspanish.domain.GrammarLevel;

import javax.servlet.http.HttpSession;

public interface GrammarService {

  GrammarLevel createGrammarLevel();

  void setLessonGrammar(HttpSession session, int lessonNumber);

  void grammarUp(HttpSession session);

}
