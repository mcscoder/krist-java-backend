package com.krist.exception.custom;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super("Forbidden");
    }

    public ForbiddenException(String msg) {
        super(msg);
    }
}
