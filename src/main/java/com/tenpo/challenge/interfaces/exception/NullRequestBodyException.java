package com.tenpo.challenge.interfaces.exception;

public class NullRequestBodyException extends RuntimeException {
    public NullRequestBodyException(String message) {
        super(message);
    }
    
}
