package com.journey.server.exceptions;

/**
 * Исключение, выбрасывается, если логин нового пользователя уже занят другим пользователем.
 */
public class LoginConflictException extends Exception {
    /**
     * Конструктор исключения
     * @param message сообщение, объясняющее причину возникновения исключения
     */
    public LoginConflictException(String message) {
        super(message);
    }
}
