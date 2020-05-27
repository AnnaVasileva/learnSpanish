package com.fmi.learnspanish.service;

import com.fmi.learnspanish.domain.VocabularyLevel;

import javax.servlet.http.HttpSession;

public interface VocabularyService {

  VocabularyLevel createVocabularyLevel();

  void categoryUpdate(HttpSession session, String vocabularyCategory);

}
