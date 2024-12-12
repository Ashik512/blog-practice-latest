package com.example.blog.features.comment.model;

import com.example.blog.common.entities.AbstractDomainBasedEntity;
import com.example.blog.features.post.model.Post;
import com.example.blog.features.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment extends AbstractDomainBasedEntity {

    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post postId;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentCommentId;

}
