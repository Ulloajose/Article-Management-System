package com.backend.api.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SuperBuilder
@Table(name = "article")
public class ArticleEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;
    private String comment;
    private String title;
    private String url;

    @Column(name = "object_id")
    private String objectId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "article_id")
    private Set<ArticleTagEntity> articleTagEntities;
}
