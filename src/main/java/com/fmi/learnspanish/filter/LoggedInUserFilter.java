package com.fmi.learnspanish.filter;

import com.fmi.learnspanish.service.AuthenticatedUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class LoggedInUserFilter implements Filter {

  @Autowired
  private AuthenticatedUserService authenticatedUserService;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String username = authenticatedUserService.getUsername();

    if (Objects.isNull(username)) {
      chain.doFilter(request, response);
      return;
    }

    HttpSession session = ((HttpServletRequest) request).getSession();
    session.setAttribute("username", username);
    chain.doFilter(request, response);
  }

}
