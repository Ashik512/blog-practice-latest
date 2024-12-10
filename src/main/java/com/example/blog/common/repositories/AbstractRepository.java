package com.example.blog.common.repositories;

import com.example.blog.common.entities.AbstractDomainBasedEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface AbstractRepository<E extends AbstractDomainBasedEntity>
        extends JpaRepository<E, Long>, JpaSpecificationExecutor<E> {

    Page<E> findAllByIsActive(Boolean isActive, Pageable pageable);

    Optional<E> findByIdAndIsActiveTrue(Long id);

}
