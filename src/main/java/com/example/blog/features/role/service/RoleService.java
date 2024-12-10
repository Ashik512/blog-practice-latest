package com.example.blog.features.role.service;
import com.example.blog.common.payloads.request.IdQuerySearchDto;
import com.example.blog.common.services.AbstractSearchService;
import com.example.blog.features.role.model.Role;
import com.example.blog.features.role.payload.request.RoleRequestDto;
import com.example.blog.features.role.payload.response.RoleResponseDto;
import com.example.blog.features.role.repository.RoleRepository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends AbstractSearchService<Role, RoleRequestDto, IdQuerySearchDto> {

    public  RoleService(RoleRepository roleRepository) {
        super(roleRepository);
    }

    @Override
    protected Role convertToEntity(RoleRequestDto roleRequestDto) {
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

    @Override
    protected Specification<Role> buildSpecification(IdQuerySearchDto searchDto) {
        return null;
    }
}
