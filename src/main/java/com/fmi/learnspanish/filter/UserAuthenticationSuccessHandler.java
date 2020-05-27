package com.fmi.learnspanish.filter;

import com.fmi.learnspanish.domain.User;
import com.fmi.learnspanish.domain.VocabularyCategory;
import com.fmi.learnspanish.domain.VocabularyCategoryType;
import com.fmi.learnspanish.domain.VocabularyLevel;
import com.fmi.learnspanish.repository.UserRepository;
import com.fmi.learnspanish.service.AuthenticatedUserService;
import com.fmi.learnspanish.service.SessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  @Autowired
  private AuthenticatedUserService authenticatedUserService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private SessionService sessionService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    String username = authenticatedUserService.getUsername();
    User user = userRepository.findByUsername(username);
    System.out.printf("user email1 --> %s%n", user.getEmail());
    System.out.printf("user getUsername --> %s%n", user.getUsername());
    System.out.printf("user getGrammarLevel().getLevel() --> %s%n", user.getGrammarLevel().getLevel());
    System.out.printf("user getVocabularyLevel().getLevel --> %s%n", user.getVocabularyLevel().getLevel());
    System.out.printf("user getPracticeLevel --> %s%n", user.getPracticeLevel());
    VocabularyLevel vl = user.getVocabularyLevel();
    Collection<VocabularyCategory> categories = user.getVocabularyLevel().getCategories();
    categories.forEach(category -> {
      if (category.getCatagoryType().equals(VocabularyCategoryType.PICTURES)) {
        System.out.printf("picturesStatus --> %s%n", category.getStatus());
      } else if (category.getCatagoryType().equals(VocabularyCategoryType.TRANSLATIONS)) {
        System.out.printf("translationsStatus --> %s%n", category.getStatus());
      } else if (category.getCatagoryType().equals(VocabularyCategoryType.ANTONYMS)) {
        System.out.printf("antonymsStatus --> %s%n", category.getStatus());
      }
    });
    System.out.println("---------------------");
    sessionService.setSessionAttributes(request.getSession(), user);

  }

}
