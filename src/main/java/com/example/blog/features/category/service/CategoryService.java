package com.example.blog.features.category.service;

import com.example.blog.common.constants.ApplicationConstant;
import com.example.blog.common.constants.ErrorId;
import com.example.blog.common.exceptions.BlogServerException;
import com.example.blog.common.payloads.request.IdQuerySearchDto;
import com.example.blog.common.services.AbstractSearchService;
import com.example.blog.common.specification.CustomSpecification;
import com.example.blog.features.category.model.Category;
import com.example.blog.features.category.payload.request.CategoryRequestDto;
import com.example.blog.features.category.payload.response.CategoryResponseDto;
import com.example.blog.features.category.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractSearchService<Category, CategoryRequestDto, IdQuerySearchDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;

    protected CategoryService(CategoryRepository categoryRepository) {
        super(categoryRepository);
        this.categoryRepository = categoryRepository;
    }

    @Override
    protected Category convertToEntity(CategoryRequestDto categoryRequestDto) {
        validateClientData(categoryRequestDto);

        Category category = new Category();
        category.setName(categoryRequestDto.getName());

        return category;
    }

    @Override
    protected Category updateEntity(CategoryRequestDto dto, Category entity) {
        entity.setName(dto.getName());

        return entity;
    }

    private void validateClientData(CategoryRequestDto categoryRequestDto) {
        if(categoryRepository.existsByName(categoryRequestDto.getName())) {
            LOGGER.error("Role name already exists");
            throw new BlogServerException(
                    ErrorId.CATEGORY_NAME_ALREADY_EXISTS,
                    HttpStatus.BAD_REQUEST,
                    MDC.get(ApplicationConstant.TRACE_ID)
            );
        }
    }

    @Override
    protected CategoryResponseDto convertToResponseDto(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    @Override
    protected Specification<Category> buildSpecification(IdQuerySearchDto searchDto) {
        CustomSpecification<Category> customSpecification = new CustomSpecification<>();
        return Specification.where(
                customSpecification.equalSpecificationAtRoot(searchDto.getIsActive(), ApplicationConstant.IS_ACTIVE_FIELD)
                        .and(customSpecification.likeSpecificationAtPrefixAndSuffix(searchDto.getQuery(), "name"))
        );
    }
}
