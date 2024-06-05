package com.backend.api.domain.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.backend.api.domain.dto.ArticleDto;
import com.backend.api.domain.model.algolia.Hit;
import com.backend.api.infrastructure.entity.ArticleEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

class ArticleMapperTest {

    @Test
    void testMapHitNull() {
        Hit hit = null;
        ArticleEntity actualMapResult = ArticleMapper.map(hit);
        assertNull(actualMapResult);
    }


    @Test
    void testMap() {
        Hit hit = new Hit();
        hit.setTags(new ArrayList<>());
        ArticleEntity actualMapResult = ArticleMapper.map(hit);

        assertNull(actualMapResult.getId());
        assertNull(actualMapResult.getCreatedBy());
        assertNull(actualMapResult.getLastModifiedBy());
        assertNull(actualMapResult.getAuthor());
        assertNull(actualMapResult.getComment());
        assertNull(actualMapResult.getObjectId());
        assertNull(actualMapResult.getTitle());
        assertNull(actualMapResult.getUrl());
        assertNull(actualMapResult.getCreatedDate());
        assertNull(actualMapResult.getLastModifiedDate());
        assertFalse(actualMapResult.isDeleted());
        assertTrue(actualMapResult.getArticleTagEntities().isEmpty());
    }


    @Test
    void testMapArticleEntity() {
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setArticleTagEntities(new HashSet<>());
        articleEntity.setAuthor("JaneDoe");
        articleEntity.setComment("Comment");
        articleEntity.setCreatedBy(1L);
        articleEntity.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        articleEntity.setDeleted(true);
        articleEntity.setId(1L);
        articleEntity.setLastModifiedBy(1L);
        articleEntity.setLastModifiedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        articleEntity.setObjectId("42");
        articleEntity.setTitle("Dr");
        articleEntity.setUrl("https://example.org/example");

        ArticleDto actualMapResult = ArticleMapper.map(articleEntity);

        assertEquals("1970-01-01T00:00:00Z", actualMapResult.getCreateDate());
        assertEquals("42", actualMapResult.getObjectId());
        assertEquals("Comment", actualMapResult.getComment());
        assertEquals("Dr", actualMapResult.getTitle());
        assertEquals("JaneDoe", actualMapResult.getAuthor());
        assertEquals("https://example.org/example", actualMapResult.getUrl());
        assertTrue(actualMapResult.getTags().isEmpty());
    }
}
