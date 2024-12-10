package com.example.blog.common.services;

import com.example.blog.common.entities.AbstractEntity;
import com.example.blog.common.payloads.IDto;
import com.example.blog.common.payloads.SDto;
import com.example.blog.common.payloads.response.PageData;
import org.springframework.data.domain.Pageable;

public interface ISearchService <E extends AbstractEntity, D extends IDto, S extends SDto> extends IService<E, D> {

    PageData search(S s, Pageable pageable);

}
