package com.example.blog.common.services;

import com.example.blog.common.entities.AbstractDomainBasedEntity;
import com.example.blog.common.payloads.IDto;
import com.example.blog.common.payloads.SDto;
import com.example.blog.common.payloads.response.PageData;
import com.example.blog.common.repositories.AbstractRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public abstract class AbstractSearchService<E extends AbstractDomainBasedEntity, D extends IDto, S extends SDto>
        extends AbstractService<E,D> implements ISearchService<E, D, S> {

    private final AbstractRepository<E> repository;

    protected AbstractSearchService(AbstractRepository<E> repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public PageData search(S searchDto, Pageable pageable) {
        return null;
    }

    protected abstract Specification<E> buildSpecification(S searchDto);

}
