package com.backend.api.infrastructure.adapter;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.backend.api.domain.dto.ArticleDto;
import com.backend.api.domain.dto.FilterArticleDto;
import com.backend.api.domain.exeption.NotFoundException;
import com.backend.api.domain.model.DataTablesResponse;
import com.backend.api.infrastructure.entity.ArticleEntity;
import com.backend.api.infrastructure.repository.ArticleRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {JpaArticleRepositoryAdapter.class})
@ExtendWith(SpringExtension.class)
class JpaArticleRepositoryAdapterTest {
    @MockBean
    private ArticleRepository articleRepository;

    @Autowired
    private JpaArticleRepositoryAdapter jpaArticleRepositoryAdapter;


    @Test
    void testValidateEndInsertRecords() {
        when(articleRepository.findAllByObjectIdIn(Mockito.any())).thenReturn(new ArrayList<>());
        when(articleRepository.saveAll(Mockito.any())).thenReturn(new ArrayList<>());

        jpaArticleRepositoryAdapter.validateEndInsertRecords(new ArrayList<>());

        verify(articleRepository).findAllByObjectIdIn(isA(Set.class));
        verify(articleRepository).saveAll(isA(Iterable.class));
    }


    @Test
    void testValidateEndInsertRecordNotFound() {
        when(articleRepository.findAllByObjectIdIn(Mockito.any()))
                .thenThrow(new NotFoundException("An error occurred"));

        assertThrows(NotFoundException.class,
                () -> jpaArticleRepositoryAdapter.validateEndInsertRecords(new ArrayList<>()));
        verify(articleRepository).findAllByObjectIdIn(isA(Set.class));
    }


    @Test
    void testDeleteArticle() {
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
        Optional<ArticleEntity> ofResult = Optional.of(articleEntity);

        when(articleRepository.save(Mockito.<ArticleEntity>any())).thenReturn(articleEntity);
        when(articleRepository.findByObjectId(Mockito.<String>any())).thenReturn(ofResult);

        jpaArticleRepositoryAdapter.deleteArticle("42", 1L);

        verify(articleRepository).findByObjectId("42");
        verify(articleRepository).save(isA(ArticleEntity.class));
    }


    @Test
    void testDeleteArticleNotFound() {
        Optional<ArticleEntity> emptyResult = Optional.empty();
        when(articleRepository.findByObjectId(Mockito.any())).thenReturn(emptyResult);

        assertThrows(NotFoundException.class, () -> jpaArticleRepositoryAdapter.deleteArticle("42", 1L));
        verify(articleRepository).findByObjectId("42");
    }

    @Test
    void testFindAll() {
        when(articleRepository.findAll(Mockito.<Specification<ArticleEntity>>any(), Mockito.any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));

        DataTablesResponse<ArticleDto> actualFindAllResult = jpaArticleRepositoryAdapter.findAll(1, 3,
                new String[]{"Sort", "com.backend.api.infrastructure.adapter.JpaArticleRepositoryAdapter"},
                new FilterArticleDto("JaneDoe", "Tag", "Dr", Month.JANUARY));

        verify(articleRepository).findAll(isA(Specification.class), isA(Pageable.class));
        assertEquals(0, actualFindAllResult.getCurrentPage());
        assertEquals(0L, actualFindAllResult.getTotalItems());
        assertEquals(1, actualFindAllResult.getTotalPages());
        assertTrue(actualFindAllResult.getData().isEmpty());
    }

    @Test
    void testFindAllNotFound() {
        when(articleRepository.findAll(Mockito.<Specification<ArticleEntity>>any(), Mockito.any()))
                .thenThrow(new NotFoundException("An error occurred"));

        assertThrows(NotFoundException.class,
                () -> jpaArticleRepositoryAdapter.findAll(1, 3,
                        new String[]{"Sort", "com.backend.api.infrastructure.adapter.JpaArticleRepositoryAdapter"},
                        new FilterArticleDto("JaneDoe", "Tag", "Dr", Month.JANUARY)));
        verify(articleRepository).findAll(isA(Specification.class), isA(Pageable.class));
    }
}
