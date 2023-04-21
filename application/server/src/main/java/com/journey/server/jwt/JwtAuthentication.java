package com.journey.server.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

public class JwtAuthentication implements Authentication {
    private boolean authenticated;
    private String username;
    private String firstName;
    private int id;
    private JwtRole role;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<String> roles = new ArrayList<String>();
        roles.add(role.getAuthority());
        return roles.stream()
                .map(JwtRole::valueOf)
                .collect(Collectors.toSet());
    }

    public Object getCredentials() { return null; }

    public Object getDetails() { return null; }

    public Object getPrincipal() { return username; }

    public boolean isAuthenticated() { return authenticated; }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setName(String name) {
        firstName = name;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public JwtRole getRole() {
        return role;
    }
    public void setRole(JwtRole role) {
        this.role = role;
    }

    public String getName() {
        return firstName;
    }
}
