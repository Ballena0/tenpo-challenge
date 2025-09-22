package com.tenpo.challenge.interfaces.exception;

public class PercentageException extends Exception{
    public PercentageException(String message) {
        super(message);
    }
    public PercentageException(String message, Throwable cause) {
        super(message, cause);
    }
}
