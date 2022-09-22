package com.elpatron.cba.api;

import com.elpatron.cba.exception.ApplicationError;
import com.elpatron.cba.exception.BadRequestException;
import com.elpatron.cba.exception.MethodNotAllowedException;
import com.elpatron.cba.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ErrorHandler extends ResponseEntityExceptionHandler {

    public static final int BAD_REQUEST = 400;
    public static final int NOT_FOUND = 404;
    public static final int METHOD_NOT_ALLOWED = 405;

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApplicationError> handleBadRequestException(BadRequestException exception) {
        ApplicationError error = new ApplicationError();
        error.setCode(BAD_REQUEST);
        error.setMessage(exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApplicationError> handleNotFoundException(NotFoundException exception) {
        ApplicationError error = new ApplicationError();
        error.setCode(NOT_FOUND);
        error.setMessage(exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<ApplicationError> handleMethodNotAllowedException(MethodNotAllowedException exception) {
        ApplicationError error = new ApplicationError();
        error.setCode(METHOD_NOT_ALLOWED);
        error.setMessage(exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }
}
