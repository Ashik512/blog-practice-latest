package com.example.blog.features.user.repository;

import com.example.blog.common.repositories.AbstractRepository;
import com.example.blog.features.user.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends AbstractRepository<User> {
    Optional<User> findByName(String name);
    boolean existsByName(String name);
}
