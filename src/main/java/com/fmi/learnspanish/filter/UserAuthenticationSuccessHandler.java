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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  @Autowired
  private AuthenticatedUserService authenticatedUserService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private SessionService sessionService;

  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    String username = authenticatedUserService.getUsername();
    User user = userRepository.findByUsername(username);
    System.out.printf("user email1 --> %s%n", user.getEmail());
    System.out.printf("user getUsername --> %s%n", user.getUsername());
    System.out.printf("user getGrammarLevel().getLevel() --> %s%n", user.getGrammarLevel().getLevel());
    System.out.printf("user getPracticeLevel --> %s%n", user.getPracticeLevel());
    System.out.printf("user getVocabularyLevel().getLevel --> %s%n", user.getVocabularyLevel().getLevel());
    System.out.printf("user getVocabularyLevel().getLesson --> %s%n", user.getVocabularyLevel().getLesson().getTitle());
    System.out.printf("user getVocabularyLevel().getCategories--> %s%n", user.getVocabularyLevel().getCategories());
    // System.out.printf("user getVocabularyLevel().getUser --> %s%n", user.getVocabularyLevel().getUser());
    System.out.printf("user --> %s%n", user);
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

    handle(request, response, authentication);

  }

  private void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {

    String targetUrl = determineTargetUrl(authentication);

    if (response.isCommitted()) {
      log.debug("Response has already been committed. Unable to redirect to " + targetUrl);
      return;
    }

    redirectStrategy.sendRedirect(request, response, targetUrl);
  }

  protected String determineTargetUrl(final Authentication authentication) {

    Map<String, String> roleTargetUrlMap = new HashMap<>();
    roleTargetUrlMap.put("USER", "/home");
    roleTargetUrlMap.put("ADMIN", "/home");

    final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    for (final GrantedAuthority grantedAuthority : authorities) {
      String authorityName = grantedAuthority.getAuthority();
      if (roleTargetUrlMap.containsKey(authorityName)) {
        return roleTargetUrlMap.get(authorityName);
      }
    }

    throw new IllegalStateException();
  }

}
