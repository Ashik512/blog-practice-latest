package com.example.blog.common.services;

import com.example.blog.common.entities.AbstractDomainBasedEntity;
import com.example.blog.common.payloads.IDto;
import com.example.blog.common.payloads.SDto;
import com.example.blog.common.payloads.response.PageData;
import com.example.blog.common.repositories.AbstractRepository;
import com.example.blog.common.specification.CustomSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.blog.common.constants.ApplicationConstant.IS_ACTIVE_FIELD;

public abstract class AbstractSearchService<E extends AbstractDomainBasedEntity, D extends IDto, S extends SDto>
        extends AbstractService<E,D> implements ISearchService<E, D, S> {

    private final AbstractRepository<E> repository;

    protected AbstractSearchService(AbstractRepository<E> repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public PageData search(S searchDto, Pageable pageable) {
        Specification<E> propellerSpecification = buildSpecification(searchDto)
                .and(new CustomSpecification<E>()
                        .active(Objects.nonNull(searchDto.getIsActive()) ? searchDto.getIsActive() : true, IS_ACTIVE_FIELD));
        Page<E> pagedData;
        if (searchDto.getIsDesc().equals(true)) {
            Sort sort = pageable.getSort().descending();
            pagedData = repository.findAll(propellerSpecification, PageRequest.of(pageable.getPageNumber(),
                    pageable.getPageSize(), sort));
        } else {
            pagedData = repository.findAll(propellerSpecification, pageable);
        }
        List<Object> models = pagedData.getContent()
                .stream().map(this::convertToResponseDto).collect(Collectors.toList());
        return PageData.builder()
                .model(models)
                .totalPages(pagedData.getTotalPages())
                .totalElements(pagedData.getTotalElements())
                .currentPage(pageable.getPageNumber() + 1)
                .build();
    }

    protected abstract Specification<E> buildSpecification(S searchDto);

}
