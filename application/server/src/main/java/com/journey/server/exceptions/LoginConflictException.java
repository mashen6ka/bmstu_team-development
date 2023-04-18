package com.journey.server.exceptions;

public class LoginConflictException extends Exception {
    public LoginConflictException(String message) {
        super(message);
    }
}
