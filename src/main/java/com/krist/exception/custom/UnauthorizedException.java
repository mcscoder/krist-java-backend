package com.krist.exception.custom;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("Unauthorized");
    }

    public UnauthorizedException(String msg) {
        super(msg);
    }
}
