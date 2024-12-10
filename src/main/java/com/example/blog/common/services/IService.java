package com.example.blog.common.services;

import com.example.blog.common.entities.AbstractEntity;
import com.example.blog.common.payloads.IDto;
import com.example.blog.common.payloads.response.PageData;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IService<E extends AbstractEntity, D extends IDto> {
    E create(D d);

    <T> T getSingle(Long id);

    E update(D d, Long id);

    PageData getAll(Boolean isActive, Pageable pageable);

    void updateActiveStatus(Long id, Boolean isActive);
}
