package com.journey.server.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequestDTO {
    private String login;
    private String hash;
}
