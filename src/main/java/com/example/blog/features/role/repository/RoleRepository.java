package com.example.blog.features.role.repository;

import com.example.blog.common.repositories.AbstractRepository;
import com.example.blog.features.role.model.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends AbstractRepository<Role> {
    Boolean existsByName(String name);
}
