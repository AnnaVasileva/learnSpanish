//All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.web.exeptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Lesson already exists.")
public class LessonAlreadyExistsException extends Exception {

	public LessonAlreadyExistsException(String message) {
		super(message);
	}

}
