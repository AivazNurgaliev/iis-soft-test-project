package com.iissoft.assignment.app.exception;

public class EtlWriterException extends RuntimeException {
    public EtlWriterException(String message) {
        super(message);
    }

    public EtlWriterException(String message, Throwable cause) {
        super(message, cause);
    }
}
