// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.filter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fmi.learnspanish.service.AuthenticatedUserService;

@Component
public class LoggedInUserFilter implements Filter {

  @Autowired
  private AuthenticatedUserService authenticatedUserService;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String username = authenticatedUserService.getUsername();
    List<String> authorities = authenticatedUserService.getRoles();

    if (Objects.isNull(username)) {
      chain.doFilter(request, response);
      return;
    }

    HttpSession session = ((HttpServletRequest) request).getSession();
    session.setAttribute("username", username);
    session.setAttribute("authorities", authorities);
    chain.doFilter(request, response);
  }

}
