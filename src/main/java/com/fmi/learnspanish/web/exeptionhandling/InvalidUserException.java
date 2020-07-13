package com.fmi.learnspanish.web.exeptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Invalid user.")
public class InvalidUserException extends Exception{
	
	public InvalidUserException(String message) {
		super(message);
	}

}
