package com.leandrosouza.associate.domain.exception;

public class EntityAlreadyExistException extends RuntimeException {

    public EntityAlreadyExistException(String message) {
        super(message);
    }
}
