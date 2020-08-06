// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.service;

import javax.servlet.http.HttpSession;

import com.fmi.learnspanish.domain.MainLevel;
import com.fmi.learnspanish.domain.VocabularyLevel;

public interface VocabularyService {

  VocabularyLevel createVocabularyLevel(MainLevel level);

  void categoryUpdate(HttpSession session, String vocabularyCategory);

}
