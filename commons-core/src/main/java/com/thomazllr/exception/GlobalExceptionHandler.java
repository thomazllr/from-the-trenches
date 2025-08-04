package com.thomazllr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<DefaultErrorMessage> handleNotFoundException(NotFoundException e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        var error = new DefaultErrorMessage(notFound.value(), e.getReason());
        return ResponseEntity.status(notFound).body(error);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<DefaultErrorMessage> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        var error = new DefaultErrorMessage(badRequest.value(), "Duplicated entry for one of the unique field");
        return ResponseEntity.status(badRequest).body(error);
    }
}
