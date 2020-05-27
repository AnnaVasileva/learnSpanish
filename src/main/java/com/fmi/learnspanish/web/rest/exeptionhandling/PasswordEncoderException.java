package com.fmi.learnspanish.web.rest.exeptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Unable to encode the password")
public class PasswordEncoderException extends RuntimeException {

  public PasswordEncoderException(String message) {
    super(message);
  }

}
