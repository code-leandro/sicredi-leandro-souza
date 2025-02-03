package com.leandrosouza.voting.domain.exception;

public class EntityAlreadyExist extends RuntimeException {

    public EntityAlreadyExist(String message) {
        super(message);
    }
}
