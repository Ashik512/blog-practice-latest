package com.example.blog.features.post.service;

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
        User user = findUserById(postRequestDto.getUserId());
        Category category = findCategoryById(postRequestDto.getCategoryId());

        return Post.builder()
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .image(postRequestDto.getImage())
                .user(user)
                .category(category)
                .build();
    }

    @Override
    protected Post updateEntity(PostRequestDto dto, Post entity) {

        User user = findUserById(dto.getUserId());
        Category category = findCategoryById(dto.getCategoryId());

        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setImage(dto.getImage());
        entity.setUser(user);
        entity.setCategory(category);

        return entity;
    }

    private User findUserById(Long userId) {
        if (userId == null) {
            return null;
        }
        return userRepository.findById(userId)
                .orElseThrow(() -> new BlogServerException(
                        ErrorId.USER_NOT_EXISTS,
                        HttpStatus.BAD_REQUEST,
                        MDC.get(ApplicationConstant.TRACE_ID)
                ));
    }

    private Category findCategoryById(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BlogServerException(
                        ErrorId.CATEGORY_NOT_EXISTS,
                        HttpStatus.BAD_REQUEST,
                        MDC.get(ApplicationConstant.TRACE_ID)
                ));
    }

    @Override
    protected PostResponseDto convertToResponseDto(Post post) {
        UserResponseDto userResponseDto = convertUserToDto(post.getUser());
        CategoryResponseDto categoryResponseDto = convertCategoryToDto(post.getCategory());

        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .image(post.getImage())
                .userResponseDto(userResponseDto)
                .categoryResponseDto(categoryResponseDto)
                .build();
    }

    private UserResponseDto convertUserToDto(User user) {
        if (user == null) {
            return null;
        }

        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }

    private CategoryResponseDto convertCategoryToDto(Category category) {
        if (category == null) {
            return null;
        }

        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    @Override
    protected Specification<Post> buildSpecification(IdQuerySearchDto searchDto) {
        CustomSpecification<Post> customSpecification = new CustomSpecification<>();
        return Specification.where(
                customSpecification.equalSpecificationAtRoot(searchDto.getIsActive(), ApplicationConstant.IS_ACTIVE_FIELD)
                        .and(customSpecification.likeSpecificationAtPrefixAndSuffix(searchDto.getQuery(), "title"))
        );
    }
}
