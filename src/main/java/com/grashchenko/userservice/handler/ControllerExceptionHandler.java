package com.grashchenko.userservice.handler;

import com.grashchenko.userservice.exception.ErrorMessage;
import com.grashchenko.userservice.exception.StorageDataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.sql.SQLException;
import java.util.Objects;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({
                IllegalArgumentException.class,
                ValidationException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorMessage handleIncorrectRequest(RuntimeException exception) {
        log.error(exception.getMessage());
        return new ErrorMessage(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorMessage handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        log.error(exception.getMessage());
        return new ErrorMessage(Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(StorageDataNotFoundException.class)
    public ErrorMessage handleStorageDataNotFoundException(RuntimeException exception) {
        log.error(exception.toString());
        return new ErrorMessage(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SQLException.class)
    public ErrorMessage handleSQLException(SQLException exception) {
        log.error(exception.toString());
        return new ErrorMessage(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorMessage handleException(MethodArgumentNotValidException exception) {
        log.error(exception.toString());
        return new ErrorMessage(exception.getMessage());
    }
}
