// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.service;

import javax.servlet.http.HttpSession;

import com.fmi.learnspanish.domain.GrammarLevel;
import com.fmi.learnspanish.domain.MainLevel;

public interface GrammarService {

  GrammarLevel createGrammarLevel(MainLevel level);

  void setLessonGrammar(HttpSession session, int lessonNumber);

  void grammarUp(HttpSession session);

}
