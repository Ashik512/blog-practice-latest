package com.example.blog.features.post.controller;

import com.example.blog.common.controllers.AbstractSearchController;
import com.example.blog.common.payloads.request.IdQuerySearchDto;
import com.example.blog.features.post.model.Post;
import com.example.blog.features.post.payload.request.PostRequestDto;
import com.example.blog.features.post.service.PostService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
public class PostController extends AbstractSearchController<Post, PostRequestDto, IdQuerySearchDto> {

    public PostController(PostService postService) {
        super(postService);
    }

}
