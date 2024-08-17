package com.krist.exception.custom;

public class ConflictException extends RuntimeException {
    public ConflictException() {
        super("Conflict");
    }

    public ConflictException(String msg) {
        super(msg);
    }
}
