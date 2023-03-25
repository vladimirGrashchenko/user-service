package com.grashchenko.userservice.handler;

import com.grashchenko.userservice.Exception.ErrorMessage;
import com.grashchenko.userservice.Exception.StorageDataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(StorageDataNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorMessage handleStorageDataNotFoundException(RuntimeException exception) {
        log.error(exception.getMessage());
        return new ErrorMessage(exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorMessage handleIllegalArgumentException(RuntimeException exception) {
        log.error(exception.getMessage());
        return new ErrorMessage(exception.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorMessage handleValidationException(RuntimeException exception) {
        log.error(exception.getMessage());
        return new ErrorMessage(exception.getMessage());
    }
}
