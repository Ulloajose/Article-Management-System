package com.backend.api.domain.mapper;

import com.backend.api.domain.dto.ArticleDto;
import com.backend.api.domain.model.algolia.Hit;
import com.backend.api.domain.util.DateUtil;
import com.backend.api.infrastructure.entity.ArticleEntity;
import com.backend.api.infrastructure.entity.ArticleTagEntity;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class ArticleMapper {

    public ArticleEntity map(Hit hit){
        if (Objects.isNull(hit)) return null;

        return ArticleEntity.builder()
                .url(hit.getStoryUrl())
                .author(hit.getAuthor())
                .createdDate(DateUtil.convertToDateTime(hit.getCreatedAt()))
                .lastModifiedDate(DateUtil.convertToDateTime(hit.getUpdatedAt()))
                .comment(hit.getCommentText())
                .objectId(hit.getObjectID())
                .title(hit.getStoryTitle())
                .articleTagEntities(mapArticleTagEntities(hit.getTags()))
                .build();
    }

    private Set<ArticleTagEntity> mapArticleTagEntities(List<String> tags){
        return tags.stream()
                .map(tag -> ArticleTagEntity.builder().name(tag).build())
                .collect(Collectors.toSet());
    }

    public ArticleDto map(ArticleEntity articleEntity){
        if (Objects.isNull(articleEntity)) return null;

        return ArticleDto.builder()
                .createDate(DateUtil.convertLocalDateTimeToString(articleEntity.getCreatedDate()))
                .title(articleEntity.getTitle())
                .url(articleEntity.getUrl())
                .comment(articleEntity.getComment())
                .tags(mapTag(articleEntity.getArticleTagEntities()))
                .author(articleEntity.getAuthor())
                .objectId(articleEntity.getObjectId())
                .build();
    }

    private List<String> mapTag(Set<ArticleTagEntity> tags){
        return tags.stream()
                .map(ArticleTagEntity::getName)
                .toList();
    }
}
