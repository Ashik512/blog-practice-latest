package com.example.blog.features.role.service;
import com.example.blog.common.constants.ApplicationConstant;
import com.example.blog.common.constants.ErrorId;
import com.example.blog.common.exceptions.BlogServerException;
import com.example.blog.common.payloads.request.IdQuerySearchDto;
import com.example.blog.common.services.AbstractSearchService;
import com.example.blog.common.specification.CustomSpecification;
import com.example.blog.features.role.model.Role;
import com.example.blog.features.role.payload.request.RoleRequestDto;
import com.example.blog.features.role.payload.response.RoleResponseDto;
import com.example.blog.features.role.repository.RoleRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RoleService extends AbstractSearchService<Role, RoleRequestDto, IdQuerySearchDto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleService.class);

    private final RoleRepository roleRepository;

    public  RoleService(RoleRepository roleRepository) {
        super(roleRepository);
        this.roleRepository = roleRepository;
    }

    @Override
    protected Role convertToEntity(RoleRequestDto roleRequestDto) {
        validateClientData(roleRequestDto);

        Role role = new Role();
        role.setName(roleRequestDto.getName());

        return role;
    }

    @Override
    protected Role updateEntity(RoleRequestDto dto, Role entity) {
        entity.setName(dto.getName());

        return entity;
    }

    @Override
    protected RoleResponseDto convertToResponseDto(Role role) {
        return RoleResponseDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();

    }

    private void validateClientData(RoleRequestDto roleRequestDto) {

        if(roleRepository.existsByName(roleRequestDto.getName())) {
            LOGGER.error("Role name already exists");
            throw new BlogServerException(ErrorId.ROLE_NAME_ALREADY_EXISTS, HttpStatus.BAD_REQUEST, MDC.get(ApplicationConstant.TRACE_ID));
        }
    }

    @Override
    protected Specification<Role> buildSpecification(IdQuerySearchDto searchDto) {
        CustomSpecification<Role> customSpecification = new CustomSpecification<>();
        return Specification.where(
                customSpecification.equalSpecificationAtRoot(searchDto.getIsActive(), ApplicationConstant.IS_ACTIVE_FIELD)
                        .and(customSpecification.likeSpecificationAtPrefixAndSuffix(searchDto.getQuery(), "name"))
        );
    }
}
