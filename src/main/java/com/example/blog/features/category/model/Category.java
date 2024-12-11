package com.example.blog.features.category.model;

import com.example.blog.common.entities.AbstractDomainBasedEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category extends AbstractDomainBasedEntity {
    private String name;
}
