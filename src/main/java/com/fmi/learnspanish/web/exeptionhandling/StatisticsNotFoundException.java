package com.fmi.learnspanish.web.exeptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Sorry, no statistivs are found.")
public class StatisticsNotFoundException extends Exception {

	public StatisticsNotFoundException(String message) {
		super(message);
	}

}
