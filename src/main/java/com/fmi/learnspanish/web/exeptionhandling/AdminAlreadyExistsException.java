// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.web.exeptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Admin user already exists.")
public class AdminAlreadyExistsException extends Exception {

	public AdminAlreadyExistsException(String message) {
		super(message);
	}

}
