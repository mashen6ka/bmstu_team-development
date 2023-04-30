package com.journey.server.utils;

import com.journey.server.jwt.JwtAuthentication;

public class JwtObjectMother {
    public static JwtAuthentication getAuthInfo() {
        JwtAuthentication auth = new JwtAuthentication();
        auth.setUsername("Мария Слепокурова");
        auth.setId(3);
        return auth;
    }

    public static JwtAuthentication getAnotherAuthInfo() {
        JwtAuthentication auth = new JwtAuthentication();
        auth.setUsername("Николай Трошкин");
        auth.setId(4);
        return auth;
    }

    public static JwtAuthentication getUnknownAuthInfo() {
        JwtAuthentication auth = new JwtAuthentication();
        auth.setUsername("Неизвестный пользователь");
        auth.setId(100);
        return auth;
    }
}
