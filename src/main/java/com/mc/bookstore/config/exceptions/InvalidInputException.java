package com.mc.bookstore.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidInputException extends RuntimeException {

  public InvalidInputException(String message) {
    super(message);
  }

  public InvalidInputException() {
    super();
  }
}
