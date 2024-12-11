package com.example.blog.features.post.model;

import com.example.blog.common.entities.AbstractDomainBasedEntity;
import com.example.blog.features.category.model.Category;
import com.example.blog.features.user.model.User;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post extends AbstractDomainBasedEntity {
    private String title;
    private String content;
    private String image;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
}
