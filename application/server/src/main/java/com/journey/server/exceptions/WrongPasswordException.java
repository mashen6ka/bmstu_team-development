package com.journey.server.exceptions;

/**
 * Исключение, выбрасывается, если при аутентификации не совпадают введенные логин и пароль.
 */
public class WrongPasswordException extends Exception {
    /**
     * Конструктор исключения
     * @param message сообщение, объясняющее причину возникновения исключения
     */
    public WrongPasswordException(String message) {
        super(message);
    }
}
