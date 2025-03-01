package com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.adapter;

import com.lian.marketing.usermicroservice.domain.model.User;
import com.lian.marketing.usermicroservice.domain.spi.IUserPersistencePort;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.mapper.IUserEntityMapper;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    @Override
    public void saveUser(User user) {
        userRepository.save(userEntityMapper.toEntity(user));
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
