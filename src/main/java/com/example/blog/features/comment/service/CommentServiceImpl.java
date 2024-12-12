package com.example.blog.features.comment.service;

import com.example.blog.common.constants.ApplicationConstant;
import com.example.blog.common.constants.ErrorId;
import com.example.blog.common.exceptions.BlogServerException;
import com.example.blog.common.utils.Helper;
import com.example.blog.features.comment.model.Comment;
import com.example.blog.features.comment.payload.request.CommentRequestDto;
import com.example.blog.features.comment.payload.response.CommentResponseDto;
import com.example.blog.features.comment.payload.response.CommentWithPostResponseDto;
import com.example.blog.features.comment.repository.CommentRepository;
import com.example.blog.features.post.model.Post;
import com.example.blog.features.post.repository.PostRepository;
import com.example.blog.features.user.model.User;
import com.example.blog.features.user.repository.UserRepository;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public void createComment(CommentRequestDto commentRequestDto) {

        Post post = findPostById(commentRequestDto.getPostId());
        User user = findUserById(commentRequestDto.getUserId());
        Comment parentComment = findParentCommentById(commentRequestDto.getParentCommentId());

        Comment comment = convertToEntity(commentRequestDto, post, user, parentComment);

        try {
            commentRepository.save(comment);
        } catch (Exception e) {
            throw BlogServerException.dataSaveException(Helper.createDynamicCode(ErrorId.DATA_NOT_SAVED_DYNAMIC, "Comment"));
        }
    }

    @Override
    public CommentWithPostResponseDto getAllCommentWithPostByPostID(Long postId) {
        // Fetch the post from the database
        Post post = postRepository.findById(postId).orElseThrow(() -> new BlogServerException(
                ErrorId.POST_NOT_EXISTS,
                HttpStatus.BAD_REQUEST,
                MDC.get(ApplicationConstant.TRACE_ID)));

        // Fetch all comments for the post in a single query
        List<Comment> allComments = commentRepository.findByPostId(postId);

        // Group comments by parentCommentId
        Map<Long, List<Comment>> commentsByParentId = allComments.stream()
                .collect(Collectors.groupingBy(comment ->
                        comment.getParentComment() != null ? comment.getParentComment().getId() : 0
                ));

        // Map top-level comments
        List<CommentResponseDto> topLevelComments = commentsByParentId.getOrDefault(0L, Collections.emptyList())
                .stream()
                .map(comment -> mapToCommentDTOWithReplies(comment, commentsByParentId))
                .collect(Collectors.toList());

        // Return the DTO with the post title and comments
        return new CommentWithPostResponseDto(postId, post.getTitle(), topLevelComments);
    }

    private CommentResponseDto mapToCommentDTOWithReplies(Comment comment, Map<Long, List<Comment>> commentsByParentId) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getUser().getId(),
                comment.getUser().getName(),
                comment.getComment(),
                commentsByParentId.getOrDefault(comment.getId(), Collections.emptyList())
                        .stream()
                        .map(childComment -> mapToCommentDTOWithReplies(childComment, commentsByParentId))
                        .collect(Collectors.toList())
        );
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new BlogServerException(
                        ErrorId.POST_NOT_EXISTS,
                        HttpStatus.BAD_REQUEST,
                        MDC.get(ApplicationConstant.TRACE_ID)
                ));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BlogServerException(
                        ErrorId.USER_NOT_EXISTS,
                        HttpStatus.BAD_REQUEST,
                        MDC.get(ApplicationConstant.TRACE_ID)
                ));
    }

    private Comment findParentCommentById(Long parentCommentId) {
        Comment parentComment = null;

        if (parentCommentId != null) {
            parentComment = commentRepository.findById(parentCommentId).orElseThrow(() -> new BlogServerException(
                    ErrorId.PARENT_COMMENT_NOT_EXISTS,
                    HttpStatus.BAD_REQUEST,
                    MDC.get(ApplicationConstant.TRACE_ID)
            ));
        }
        return parentComment;
    }

    private Comment convertToEntity(CommentRequestDto commentRequestDto, Post post, User user, Comment parentComment) {

        Comment comment = new Comment();

        comment.setComment(commentRequestDto.getComment());
        comment.setPost(post);
        comment.setUser(user);
        comment.setParentComment(parentComment);

        return comment;
    }

    private CommentResponseDto convertToResponseDto(Comment comment) {
        CommentResponseDto commentResponseDto = new CommentResponseDto();

        commentResponseDto.setComment(comment.getComment());
        commentResponseDto.setUserId(comment.getUser().getId());
        commentResponseDto.setUserName(comment.getUser().getName());
        // commentResponseDto.setReplies();

        return commentResponseDto;
    }
}
