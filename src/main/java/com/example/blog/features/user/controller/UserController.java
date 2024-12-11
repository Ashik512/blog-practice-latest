package com.example.blog.features.user.controller;

import com.example.blog.common.controllers.AbstractSearchController;
import com.example.blog.common.payloads.request.IdQuerySearchDto;
import com.example.blog.features.user.model.User;
import com.example.blog.features.user.payload.request.UserRequestDto;
import com.example.blog.features.user.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController extends AbstractSearchController<User, UserRequestDto, IdQuerySearchDto> {

    public UserController(UserService userService) {
        super(userService);
    }
}
