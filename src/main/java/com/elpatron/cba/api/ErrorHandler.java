package com.elpatron.cba.api;

import com.elpatron.cba.exception.ApplicationError;
import com.elpatron.cba.exception.BadRequestException;
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

    public static final int NOT_FOUND = 101;
    public static final int BAD_REQUEST = 102;

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApplicationError> handleNotFoundException(NotFoundException exception) {
        ApplicationError error = new ApplicationError();
        error.setCode(NOT_FOUND);
        error.setMessage(exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApplicationError> handleBadRequestException(BadRequestException exception) {
        ApplicationError error = new ApplicationError();
        error.setCode(BAD_REQUEST);
        error.setMessage(exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
