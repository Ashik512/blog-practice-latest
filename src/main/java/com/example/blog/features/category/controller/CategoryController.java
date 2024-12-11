package com.example.blog.features.category.controller;

import com.example.blog.common.controllers.AbstractSearchController;
import com.example.blog.common.payloads.request.IdQuerySearchDto;
import com.example.blog.features.category.model.Category;
import com.example.blog.features.category.payload.request.CategoryRequestDto;
import com.example.blog.features.category.service.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController extends AbstractSearchController<Category, CategoryRequestDto, IdQuerySearchDto> {

    public CategoryController(CategoryService categoryService) {
        super(categoryService);
    }

}
