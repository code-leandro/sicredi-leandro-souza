package com.leandrosouza.voting.application.exception;

public class UnavailableSystemException extends RuntimeException {

    public UnavailableSystemException(String message) {
        super(message);
    }
}
