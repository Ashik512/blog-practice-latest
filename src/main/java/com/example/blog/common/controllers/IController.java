package com.example.blog.common.controllers;

import com.example.blog.common.entities.AbstractDomainBasedEntity;
import com.example.blog.common.entities.AbstractEntity;
import com.example.blog.common.payloads.IDto;
import com.example.blog.common.payloads.response.MessageResponse;
import com.example.blog.common.payloads.response.PageData;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IController <E extends AbstractEntity, D extends IDto> {
    PageData getAll(Boolean isActive, Pageable pageable);

    <T>T getSingle(Long id);

    ResponseEntity<MessageResponse> create(D d);

    ResponseEntity<MessageResponse> update(D d, Long id);

    ResponseEntity<MessageResponse> updateActiveStatus(Long id, Boolean isActive);
}
