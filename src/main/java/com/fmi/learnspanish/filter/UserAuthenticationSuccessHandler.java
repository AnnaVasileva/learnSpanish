// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fmi.learnspanish.domain.User;
import com.fmi.learnspanish.repository.UserRepository;
import com.fmi.learnspanish.service.AuthenticatedUserService;
import com.fmi.learnspanish.service.SessionService;

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
