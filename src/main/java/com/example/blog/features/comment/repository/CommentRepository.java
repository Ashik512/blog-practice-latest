package com.example.blog.features.comment.repository;

import com.example.blog.features.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(Long postId);
    List<Comment> findByParentCommentId(Long parentCommentId);

}
