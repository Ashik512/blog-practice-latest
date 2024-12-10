package com.example.blog.features.role.model;

import com.example.blog.common.entities.AbstractDomainBasedEntity;
import com.example.blog.features.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class Role extends AbstractDomainBasedEntity {
    @Column(unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "role", cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<User> users;

}
