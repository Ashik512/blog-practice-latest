package com.example.blog.features.post.repository;

import com.example.blog.common.repositories.AbstractRepository;
import com.example.blog.features.post.model.Post;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends AbstractRepository<Post> {
}
