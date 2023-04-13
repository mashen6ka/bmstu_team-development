package com.journey.server.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserEntity {
    String login;
    String hash;
    String name;
}
