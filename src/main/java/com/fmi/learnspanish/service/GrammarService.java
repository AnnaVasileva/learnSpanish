package com.fmi.learnspanish.service;

import com.fmi.learnspanish.domain.GrammarLevel;

import javax.servlet.http.HttpSession;

public interface GrammarService {

  GrammarLevel createGrammarLevel();

  void grammarUp(HttpSession session);
}
