package com.leandrosouza.voting.application.exception;

import com.leandrosouza.voting.domain.exception.EntityAlreadyExist;
import com.leandrosouza.voting.domain.exception.EntityNotFoundException;
import com.leandrosouza.voting.domain.exception.TopicCannotBeOpenedException;
import com.leandrosouza.voting.domain.exception.VoteCannotBeCastException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

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

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> handleEntityNotFoundExceptions(
            EntityNotFoundException ex,
            HttpServletRequest request) {

        StandardError standardError = StandardError.builder()
                .error(ex.getMessage())
                .path(request.getRequestURI())
                .status(HttpStatus.NOT_FOUND.value())
                .time(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(standardError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExist.class)
    public ResponseEntity<StandardError> handleEntityAlreadyExistExceptions(EntityAlreadyExist ex,
                                                                            HttpServletRequest request) {
        StandardError standardError = StandardError.builder()
                .error(ex.getMessage())
                .path(request.getRequestURI())
                .status(HttpStatus.OK.value())
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(standardError, HttpStatus.OK);
    }

    @ExceptionHandler(TopicCannotBeOpenedException.class)
    public ResponseEntity<StandardError> handleTopicAlreadyOpenExceptions(TopicCannotBeOpenedException ex,
                                                                          HttpServletRequest request) {
        StandardError standardError = StandardError.builder()
                .error(ex.getMessage())
                .path(request.getRequestURI())
                .status(HttpStatus.CONFLICT.value())
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(standardError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(VoteCannotBeCastException.class)
    public ResponseEntity<StandardError> handleVoteCannotBeCastExceptions(VoteCannotBeCastException ex,
                                                                          HttpServletRequest request) {
        StandardError standardError = StandardError.builder()
                .error(ex.getMessage())
                .path(request.getRequestURI())
                .status(HttpStatus.CONFLICT.value())
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(standardError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnavailableSystemException.class)
    public ResponseEntity<StandardError> unavailableSystemExceptions(UnavailableSystemException ex,
                                                                          HttpServletRequest request) {
        StandardError standardError = StandardError.builder()
                .error(ex.getMessage())
                .path(request.getRequestURI())
                .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(standardError, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
