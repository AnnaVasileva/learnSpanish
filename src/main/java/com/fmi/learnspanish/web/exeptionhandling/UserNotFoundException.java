// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.web.exeptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Sorry, user is not found.")
public class UserNotFoundException extends Exception {

	public UserNotFoundException(String message) {
		super(message);
	}

}