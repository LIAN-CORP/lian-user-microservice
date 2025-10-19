package com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.adapter;

import com.lian.marketing.usermicroservice.domain.model.User;
import com.lian.marketing.usermicroservice.domain.spi.IUserPersistencePort;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.entity.UserEntity;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.mapper.IUserEntityMapper;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UserAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    @Override
    public UUID saveUser(User user) {
        UserEntity entity = userRepository.save(userEntityMapper.toEntity(user));
        return entity.getId();
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email).map(userEntityMapper::toModel);
    }

    @Override
    public boolean userExistsById(UUID id) {
        return userRepository.existsById(id);
    }

    @Override
    public Optional<User> findUserById(UUID id) {
        return userRepository.findById(id).map(userEntityMapper::toModel);
    }

    @Override
    public UUID findAnyAdminUser() {
        return userRepository.findAnyAdminUser();
    }
}
