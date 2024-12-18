package com.example.blog.features.user.model;

import com.example.blog.common.entities.AbstractDomainBasedEntity;
import com.example.blog.features.role.model.Role;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.*;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends AbstractDomainBasedEntity {
    @NotBlank(message = "username must not be blank")
    @Column(unique = true)
    private String name;

    @NotBlank(message = "password must not be blank")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;
}
