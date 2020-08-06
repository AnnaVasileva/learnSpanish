// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.fmi.learnspanish.service.AuthenticatedUserService;

@Component
public class AuthenticatedUserServiceImpl implements AuthenticatedUserService {

	@Override
	public String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	@Override
	public List<String> getRoles() {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities()//
				.stream()//
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

	}
}
