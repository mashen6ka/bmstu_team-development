package com.journey.server.exceptions;

public class LoginConflictException extends Exception {

    @Override
    public String getMessage() {
        return "Данный логин уже занят другим пользователем.";
    }
}
