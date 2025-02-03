package com.leandrosouza.voting.domain.exception;

public class TopicCannotBeOpenedException extends RuntimeException {

    public TopicCannotBeOpenedException(String message) {
        super(message);
    }
}