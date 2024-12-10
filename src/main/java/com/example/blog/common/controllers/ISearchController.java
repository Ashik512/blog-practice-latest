package com.example.blog.common.controllers;

import com.example.blog.common.entities.AbstractEntity;
import com.example.blog.common.payloads.IDto;
import com.example.blog.common.payloads.SDto;
import com.example.blog.common.payloads.response.PageData;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ISearchController<E extends AbstractEntity, D extends IDto, S extends SDto> extends IController<E, D> {
    ResponseEntity<PageData> search(S s, Pageable pageable);
}
