package com.example.blog.common.controllers;

import com.example.blog.common.entities.AbstractDomainBasedEntity;
import com.example.blog.common.payloads.IDto;
import com.example.blog.common.payloads.SDto;
import com.example.blog.common.payloads.response.PageData;
import com.example.blog.common.services.ISearchService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public class AbstractSearchController<E extends AbstractDomainBasedEntity, D extends IDto, S extends SDto>
        extends AbstractController<E,D> implements ISearchController<E,D,S> {

    protected final ISearchService<E, D, S> iSearchService;

    public AbstractSearchController(ISearchService<E, D, S> iSearchService) {
        super(iSearchService);
        this.iSearchService = iSearchService;
    }

    @Override
    public ResponseEntity<PageData> search(S s, Pageable pageable) {
        return null;
    }
}
