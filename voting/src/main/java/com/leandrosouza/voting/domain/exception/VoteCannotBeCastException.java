package com.leandrosouza.voting.domain.exception;

public class VoteCannotBeCastException extends RuntimeException {

    public VoteCannotBeCastException(String message) {
        super(message);
    }
}
