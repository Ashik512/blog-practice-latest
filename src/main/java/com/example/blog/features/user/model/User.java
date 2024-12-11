package com.example.blog.features.user.model;

import com.example.blog.common.entities.AbstractDomainBasedEntity;
import com.example.blog.features.role.model.Role;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.*;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends AbstractDomainBasedEntity {
    @NotBlank
    @Size(max = 20)
    @Column(unique = true)
    private String name;

    @NotBlank
    @Size(max = 20)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;
}
