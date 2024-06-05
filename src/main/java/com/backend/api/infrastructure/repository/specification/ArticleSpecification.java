package com.backend.api.infrastructure.repository.specification;

import com.backend.api.infrastructure.entity.ArticleEntity;
import com.backend.api.infrastructure.entity.ArticleTagEntity;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.time.Month;
import java.util.Objects;

@UtilityClass
public class ArticleSpecification {

    public Specification<ArticleEntity> notDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("deleted"));
    }

    public Specification<ArticleEntity> authorLike(String author) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.nonNull(author) && !author.isEmpty()) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), "%" + author.toLowerCase() + "%");
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }

    public Specification<ArticleEntity> titleLike(String title) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.nonNull(title) && !title.isEmpty()) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }

    public Specification<ArticleEntity> inTag(String tag) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.nonNull(tag) && !tag.isEmpty()) {
                Join<ArticleEntity, ArticleTagEntity> articleTagEntities = root.join("articleTagEntities");
                return criteriaBuilder.like(
                        criteriaBuilder.lower(articleTagEntities.get("name")), "%" + tag.toLowerCase() + "%");
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }

    public Specification<ArticleEntity> byMonth(Month month) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.nonNull(month)) {
                Expression<String> monthExpression = criteriaBuilder.function(
                        "TO_CHAR", String.class, root.get("createdDate"), criteriaBuilder.literal("MONTH"));
                return criteriaBuilder.like(monthExpression, "%" + month.name() + "%");
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }
}
