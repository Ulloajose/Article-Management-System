package com.backend.api.infrastructure.repository;

import com.backend.api.infrastructure.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
    List<ArticleEntity> findAllByObjectIdIn(Set<String> objectIds);
    Page<ArticleEntity> findAll(Specification<ArticleEntity> spec, Pageable pageable);
    Optional<ArticleEntity> findByObjectId(String objectId);
}
