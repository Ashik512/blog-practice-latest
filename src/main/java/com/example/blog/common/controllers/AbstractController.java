package com.example.blog.common.controllers;

import com.example.blog.common.constants.ApplicationConstant;
import com.example.blog.common.entities.AbstractDomainBasedEntity;
import com.example.blog.common.payloads.IDto;
import com.example.blog.common.payloads.response.MessageResponse;
import com.example.blog.common.payloads.response.PageData;
import com.example.blog.common.services.IService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
public abstract class AbstractController<E extends AbstractDomainBasedEntity, D extends IDto> implements
        IController<E, D> {

    protected final IService<E, D> service;
    private static final String CREATED_SUCCESSFULLY_MESSAGE = "Created Successfully";
    private static final String UPDATED_SUCCESSFULLY_MESSAGE = "Updated Successfully";
    private static final String ACTIVE_STATUS_CHANGED_SUCCESSFULLY_MESSAGE = "Active Status Changed Successfully";

    @Override
    @GetMapping
    public PageData getAll(@Nullable @RequestParam(value = "active", defaultValue = "true") Boolean isActive,
                           @PageableDefault(sort = ApplicationConstant.DEFAULT_SORT,
                                   direction = Sort.Direction.ASC) Pageable pageable) {
        return service.getAll(isActive, pageable);
    }

    @Override
    @GetMapping("/{id}")
    public <T> T getSingle(@PathVariable Long id) {
        return service.getSingle(id);
    }

    @Transactional
    @Override
    @PostMapping
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody D d) {
        return ResponseEntity.ok(new MessageResponse(CREATED_SUCCESSFULLY_MESSAGE, service.create(d).getId()));
    }

    @Transactional
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> update(@Valid @RequestBody D d, @PathVariable Long id) {
        return ResponseEntity.ok(new MessageResponse(UPDATED_SUCCESSFULLY_MESSAGE, service.update(d, id).getId()));
    }

    @Transactional
    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<MessageResponse> updateActiveStatus(@PathVariable Long id, @RequestParam("active") Boolean isActive) {
        service.updateActiveStatus(id, isActive);
        return ResponseEntity.ok(new MessageResponse(ACTIVE_STATUS_CHANGED_SUCCESSFULLY_MESSAGE));
    }

}
