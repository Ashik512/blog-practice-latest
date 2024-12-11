package com.example.blog.common.services;

import com.example.blog.common.constants.ErrorId;
import com.example.blog.common.entities.AbstractDomainBasedEntity;
import com.example.blog.common.exceptions.BlogServerException;
import com.example.blog.common.payloads.IDto;
import com.example.blog.common.payloads.SDto;
import com.example.blog.common.payloads.response.PageData;
import com.example.blog.common.repositories.AbstractRepository;
import com.example.blog.common.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.blog.common.constants.ApplicationConstant.FIRST_INDEX;

@RequiredArgsConstructor
public abstract class AbstractService<E extends AbstractDomainBasedEntity, D extends IDto> implements IService<E, D> {

    private final AbstractRepository<E> repository;

    @Override
    public PageData getAll(Boolean isActive, Pageable pageable) {
        Page<E> pagedData = repository.findAllByIsActive(isActive, pageable);
        List<Object> models = pagedData.getContent().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
        return PageData.builder()
                .model(models)
                .totalPages(pagedData.getTotalPages())
                .totalElements(pagedData.getTotalElements())
                .currentPage(pageable.getPageNumber() + 1)
                .build();
    }

    @Override
    public E create(D d) {
        E entity = convertToEntity(d);
        return saveItem(entity);
    }

    @Override
    public E update(D d, Long id) {
        final E entity = updateEntity(d, findByIdUnfiltered(id));
        return saveItem(entity);
    }

    // returns both active/inactive data
    @Override
    public <T> T getSingle(Long id) {
        return convertToResponseDto(findByIdUnfiltered(id));
    }

    @Override
    public void updateActiveStatus(Long id, Boolean isActive) {
        E e = findByIdUnfiltered(id);
        if (e.getIsActive() == isActive) {
            throw BlogServerException.badRequest(ErrorId.ONLY_TOGGLE_VALUE_ACCEPTED);
        }

        e.setIsActive(isActive);
        saveItem(e);
    }

    // Returns only active data
    public E findById(Long id) {
        return findOptionalById(id, true).orElseThrow(() -> buildException(ErrorId.NOT_FOUND_DYNAMIC,
                ErrorId.DATA_NOT_FOUND));
    }

    // Returns both active/inactive data
    public E findByIdUnfiltered(Long id) {
        return findOptionalById(id, false).orElseThrow(() -> buildException(ErrorId.NOT_FOUND_DYNAMIC,
                ErrorId.DATA_NOT_FOUND));
    }

    public Optional<E> findOptionalById(Long id, boolean activeRequired) {
        if (ObjectUtils.isEmpty(id)) {
            throw buildException(ErrorId.ID_IS_REQUIRED_DYNAMIC, ErrorId.ID_IS_REQUIRED);
        }
        return activeRequired ? repository.findByIdAndIsActiveTrue(id) : repository.findById(id);
    }

    public E saveItem(E entity) {
        try {
            return repository.save(entity);
        } catch (Exception e) {
            String name = entity.getClass().getSimpleName();
            throw BlogServerException.dataSaveException(Helper.createDynamicCode(ErrorId.DATA_NOT_SAVED_DYNAMIC, name));
        }
    }

    private BlogServerException buildException(String dynamicMessage, String staticMessage) {
        try {
            String typeName = ((Class<?>) (((ParameterizedType) this.getClass().getGenericSuperclass())
                    .getActualTypeArguments()[FIRST_INDEX])).getSimpleName();
            return BlogServerException.notFound(Helper.createDynamicCode(dynamicMessage,
                    typeName));
        } catch (Exception e) {
            return BlogServerException.notFound(staticMessage);
        }
    }

    protected abstract E convertToEntity(D d);

    protected abstract E updateEntity(D dto, E entity);

    protected abstract <T> T convertToResponseDto(E e);
}
