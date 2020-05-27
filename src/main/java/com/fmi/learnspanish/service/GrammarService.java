package com.fmi.learnspanish.service;

import com.fmi.learnspanish.domain.GrammarLevel;

public interface GrammarService {

  GrammarLevel createGrammarLevel();

  int grammarUp(String email);
}
