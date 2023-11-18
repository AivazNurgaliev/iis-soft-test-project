package com.iissoft.assignment.app.exception;

public class EtlReaderException extends RuntimeException {
    public EtlReaderException(String message) {
        super(message);
    }

    public EtlReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
