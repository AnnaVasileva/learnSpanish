package com.fmi.learnspanish.service.impl;

import com.fmi.learnspanish.domain.User;
import com.fmi.learnspanish.domain.VocabularyCategory;
import com.fmi.learnspanish.domain.VocabularyCategoryType;
import com.fmi.learnspanish.service.SessionService;

import org.springframework.stereotype.Service;

import java.util.Collection;

import javax.servlet.http.HttpSession;

@Service
public class SessionServiceImpl implements SessionService {

  @Override
  public void setSessionAttributes(HttpSession session, User user) {
    session.setAttribute("email", user.getEmail());
    session.setAttribute("username", user.getUsername());
    session.setAttribute("grammarLevel", user.getGrammarLevel().getLevel());
    session.setAttribute("grammarLessonTitle", user.getGrammarLevel().getLesson().getTitle());
    session.setAttribute("vocabularyLevel", user.getVocabularyLevel().getLevel());
    session.setAttribute("vocabularyLessonTitle", user.getVocabularyLevel().getLesson().getTitle());

    Collection<VocabularyCategory> categories = user.getVocabularyLevel().getCategories();
    categories.forEach(category -> {
      if (category.getCatagoryType().equals(VocabularyCategoryType.PICTURES)) {
        session.setAttribute("picturesStatus", category.getStatus());
      } else if (category.getCatagoryType().equals(VocabularyCategoryType.TRANSLATIONS)) {
        session.setAttribute("translationsStatus", category.getStatus());
      } else if (category.getCatagoryType().equals(VocabularyCategoryType.ANTONYMS)) {
        session.setAttribute("antonymsStatus", category.getStatus());
      }
    });

    session.setAttribute("practiceLevel", user.getPracticeLevel());
  }

}