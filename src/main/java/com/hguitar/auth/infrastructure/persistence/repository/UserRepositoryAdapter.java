package com.hguitar.auth.infrastructure.persistence.repository;

import com.hguitar.auth.domain.model.User;
import com.hguitar.auth.domain.repository.UserRepository;
import com.hguitar.auth.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpa;

    public UserRepositoryAdapter(JpaUserRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public User save(User user) {
        UserEntity e = new UserEntity(user.getId(), user.getEmail(),
                user.getPasswordHash(), user.isActive(), user.getRoles());
        return toDomain(jpa.save(e));
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpa.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpa.findByEmail(email).map(this::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpa.existsByEmail(email);
    }

    private User toDomain(UserEntity e) {
        return new User(e.getId(), e.getEmail(), e.getPasswordHash(),
                e.isActive(), e.getRoles(), e.getCreatedAt(), e.getUpdatedAt());
    }
}
