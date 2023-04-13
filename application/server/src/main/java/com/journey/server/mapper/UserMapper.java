package com.journey.server.mapper;

import com.journey.server.dto.user.RegistryUserDTO;
import com.journey.server.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserEntity fromRegistryUserDTO(RegistryUserDTO dto) {
        return UserEntity.builder()
                .login(dto.getLogin())
                .hash(dto.getHash())
                .name(dto.getName())
                .build();
    }
}
