package com.journey.server.jwt;

import io.jsonwebtoken.Claims;

public class JwtUtils {
    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRole(getRole(claims));
        jwtInfoToken.setId(claims.get("id", Integer.class));
        jwtInfoToken.setUsername(claims.getSubject());
        return jwtInfoToken;
    }

    private static JwtRole getRole(Claims claims) {
//        final JwtRole role = JwtRole.valueOf(claims.get("roles", String.class));
        return JwtRole.USER;
    }
}