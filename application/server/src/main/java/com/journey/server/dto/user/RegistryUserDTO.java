package com.journey.server.dto.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegistryUserDTO {
    String login;
    String hash;
    String name;
}
