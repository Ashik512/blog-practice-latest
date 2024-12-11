package com.example.blog.features.user.service;

import com.example.blog.common.constants.ApplicationConstant;
import com.example.blog.common.constants.ErrorId;
import com.example.blog.common.exceptions.BlogServerException;
import com.example.blog.common.payloads.request.IdQuerySearchDto;
import com.example.blog.common.services.AbstractSearchService;
import com.example.blog.common.specification.CustomSpecification;
import com.example.blog.features.role.model.Role;
import com.example.blog.features.role.repository.RoleRepository;
import com.example.blog.features.user.model.User;
import com.example.blog.features.user.payload.request.UserRequestDto;
import com.example.blog.features.user.payload.response.UserResponseDto;
import com.example.blog.features.user.repository.UserRepository;
import org.slf4j.MDC;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class UserService extends AbstractSearchService<User, UserRequestDto, IdQuerySearchDto> {

    private final RoleRepository roleRepository;

    protected UserService(UserRepository userRepository, RoleRepository roleRepository) {
        super(userRepository);
        this.roleRepository = roleRepository;
    }

    @Override
    protected User convertToEntity(UserRequestDto userRequestDto) {
        Role existingRole = roleRepository.findById(userRequestDto.getRoleId())
                .orElseThrow(() -> new BlogServerException(
                        ErrorId.ROLE_NOT_EXISTS,
                        HttpStatus.NOT_FOUND,
                        MDC.get(ApplicationConstant.TRACE_ID)
                ));

         return User.builder()
                .name(userRequestDto.getName())
                .password(userRequestDto.getPassword())
                .role(existingRole)
                .build();
    }

    @Override
    protected User updateEntity(UserRequestDto dto, User entity) {
        Role newRole = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new BlogServerException(
                        ErrorId.ROLE_NOT_EXISTS,
                        HttpStatus.NOT_FOUND,
                        MDC.get(ApplicationConstant.TRACE_ID)
                ));

        entity.setName(dto.getName());
        entity.setPassword(dto.getPassword());
        entity.setRole(newRole);

        return entity;
    }

    @Override
    protected UserResponseDto convertToResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }

    @Override
    protected Specification<User> buildSpecification(IdQuerySearchDto searchDto) {
        CustomSpecification<User> customSpecification = new CustomSpecification<>();
        return Specification.where(
                customSpecification.equalSpecificationAtRoot(searchDto.getIsActive(), ApplicationConstant.IS_ACTIVE_FIELD)
                        .and(customSpecification.likeSpecificationAtPrefixAndSuffix(searchDto.getQuery(), "name"))
        );
    }
}
