package com.tenpo.challenge.interfaces.exception;

public class NullOperandException extends RuntimeException {
    public NullOperandException(String message) {
        super(message);
    }
}
