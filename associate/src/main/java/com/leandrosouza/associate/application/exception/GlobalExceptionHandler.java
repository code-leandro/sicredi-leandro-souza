package com.leandrosouza.associate.application.exception;

import com.leandrosouza.associate.domain.exception.CPFInvalidQueryException;
import com.leandrosouza.associate.domain.exception.CPFInvalidToStoreException;
import com.leandrosouza.associate.domain.exception.EntityAlreadyExistException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final String CPF_IS_NOT_VALID = "CPF is not valid";
    public static final String ENTITY_ALREADY_EXIST = "Entity already exist";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleTopicAlreadyOpenExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String field = ex.getFieldError() != null ? ex.getFieldError().getField() : "";
        String message = ex.getFieldError().getDefaultMessage();
        String error = field + ": " + message;

        StandardError standardError = StandardError.builder()
                .error(error)
                .path(request.getRequestURI())
                .status(HttpStatus.BAD_REQUEST.value())
                .time(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(standardError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CPFInvalidQueryException.class)
    public ResponseEntity<Void> handleCPFInvalidExceptions(
            CPFInvalidQueryException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(CPFInvalidToStoreException.class)
    public ResponseEntity<StandardError> handleCPFInvalidToStoreExceptions(
            CPFInvalidToStoreException ex,
            HttpServletRequest request) {

        StandardError standardError = StandardError.builder()
                .error(CPF_IS_NOT_VALID)
                .path(request.getRequestURI())
                .status(HttpStatus.BAD_REQUEST.value())
                .time(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(standardError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<StandardError> handleAlreadyExistExceptions(
            EntityAlreadyExistException ex,
            HttpServletRequest request) {

        StandardError standardError = StandardError.builder()
                .error(ENTITY_ALREADY_EXIST)
                .path(request.getRequestURI())
                .status(HttpStatus.CONFLICT.value())
                .time(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(standardError, HttpStatus.CONFLICT);
    }
}
