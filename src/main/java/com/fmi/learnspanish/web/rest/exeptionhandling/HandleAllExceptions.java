package com.fmi.learnspanish.web.rest.exeptionhandling;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class HandleAllExceptions {
  private static final String MESSAGE = "message";
  private ModelAndView modelAndView = new ModelAndView("error.html");

  @ExceptionHandler(Throwable.class)
  public ModelAndView handleUserNotFoundException(Throwable exception) {

    String exceptionMessage = exception.getMessage();
    if (exceptionMessage.contains("NumberFormatException")) {
      log.error("Failed to convert value", exception);
      modelAndView.addObject(MESSAGE, "Sorry, page not found");
    } else {
      modelAndView.addObject(exception.getMessage());
    }

    return modelAndView;
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ModelAndView handleUserNotFoundException(UserNotFoundException exception) {
    modelAndView.addObject(MESSAGE, exception.getMessage());
    return modelAndView;
  }

  @ExceptionHandler(PasswordEncoderException.class)
  public ModelAndView handlePasswordEncoderException(PasswordEncoderException exception) {
    modelAndView.addObject(MESSAGE, exception.getMessage());
    return modelAndView;
  }

}
