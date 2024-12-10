package com.example.blog.features.role.controller;

import com.example.blog.common.controllers.AbstractSearchController;
import com.example.blog.common.payloads.request.IdQuerySearchDto;
import com.example.blog.features.role.model.Role;
import com.example.blog.features.role.payload.request.RoleRequestDto;
import com.example.blog.features.role.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
public class RoleController extends AbstractSearchController<Role, RoleRequestDto, IdQuerySearchDto> {

    public RoleController(RoleService roleService) {
        super(roleService);
    }
}
