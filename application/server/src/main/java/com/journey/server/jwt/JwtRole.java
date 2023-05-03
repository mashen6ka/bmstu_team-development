package com.journey.server.jwt;

import org.springframework.security.core.GrantedAuthority;

public enum JwtRole implements GrantedAuthority {
    USER("USER");

    String role;

    JwtRole(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
