package com.example.blog.features.post.service;
;
import com.example.blog.common.constants.ApplicationConstant;
import com.example.blog.common.constants.ErrorId;
import com.example.blog.common.exceptions.BlogServerException;
import com.example.blog.common.payloads.request.IdQuerySearchDto;
import com.example.blog.common.repositories.AbstractRepository;
import com.example.blog.common.services.AbstractSearchService;
import com.example.blog.common.specification.CustomSpecification;
import com.example.blog.features.category.model.Category;
import com.example.blog.features.category.payload.response.CategoryResponseDto;
import com.example.blog.features.category.repository.CategoryRepository;
import com.example.blog.features.post.model.Post;
import com.example.blog.features.post.payload.request.PostRequestDto;
import com.example.blog.features.post.payload.response.PostResponseDto;
import com.example.blog.features.post.repository.PostRepository;
import com.example.blog.features.user.model.User;
import com.example.blog.features.user.payload.response.UserResponseDto;
import com.example.blog.features.user.repository.UserRepository;
import org.slf4j.MDC;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PostService extends AbstractSearchService<Post, PostRequestDto, IdQuerySearchDto> {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    protected PostService(PostRepository postRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        super(postRepository);
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    protected Post convertToEntity(PostRequestDto postRequestDto) {
        User user = null;
        Category category = null;

        if(Objects.nonNull(postRequestDto.getUserId())) {
            user = userRepository.findById(postRequestDto.getUserId()).orElseThrow(() -> new BlogServerException(
                    ErrorId.USER_NOT_EXISTS,
                    HttpStatus.BAD_REQUEST,
                    MDC.get(ApplicationConstant.TRACE_ID)
            ));
        }

        if(Objects.nonNull(postRequestDto.getCategoryId())) {
            category = categoryRepository.findById(postRequestDto.getCategoryId()).orElseThrow(() -> new BlogServerException(
                    ErrorId.CATEGORY_NOT_EXISTS,
                    HttpStatus.BAD_REQUEST,
                    MDC.get(ApplicationConstant.TRACE_ID)
            ));
        }

        Post post = new Post();

        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        post.setImage(postRequestDto.getImage());
        post.setUser(user);
        post.setCategory(category);

        return post;
    }

    @Override
    protected Post updateEntity(PostRequestDto dto, Post entity) {

        User user = null;
        Category category = null;

        if(Objects.nonNull(dto.getUserId())) {
            user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new BlogServerException(
                    ErrorId.USER_NOT_EXISTS,
                    HttpStatus.BAD_REQUEST,
                    MDC.get(ApplicationConstant.TRACE_ID)
            ));
        }

        if(Objects.nonNull(dto.getCategoryId())) {
            category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(() -> new BlogServerException(
                    ErrorId.CATEGORY_NOT_EXISTS,
                    HttpStatus.BAD_REQUEST,
                    MDC.get(ApplicationConstant.TRACE_ID)
            ));
        }

        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setImage(dto.getImage());
        entity.setUser(user);
        entity.setCategory(category);

        return entity;
    }

    @Override
    protected PostResponseDto convertToResponseDto(Post post) {

        User user = post.getUser();
        UserResponseDto userResponseDto = null;

        if(user != null) {
            userResponseDto = UserResponseDto.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .role(user.getRole())
                    .build();
        }

        Category category = post.getCategory();
        CategoryResponseDto categoryResponseDto = null;

        if(category != null) {
            categoryResponseDto = CategoryResponseDto.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .build();
        }

        return PostResponseDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .image(post.getImage())
                .userResponseDto(userResponseDto)
                .categoryResponseDto(categoryResponseDto)
                .build();
    }

    @Override
    protected Specification<Post> buildSpecification(IdQuerySearchDto searchDto) {
        CustomSpecification<Post> customSpecification = new CustomSpecification<>();
        return Specification.where(
                customSpecification.equalSpecificationAtRoot(searchDto.getIsActive(), ApplicationConstant.IS_ACTIVE_FIELD)
                        .and(customSpecification.likeSpecificationAtPrefixAndSuffix(searchDto.getQuery(), "name"))
        );
    }
}
