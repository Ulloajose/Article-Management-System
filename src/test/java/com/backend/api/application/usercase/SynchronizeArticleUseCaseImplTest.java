package com.backend.api.application.usercase;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.backend.api.domain.model.algolia.AlgoliaResponse;
import com.backend.api.domain.port.out.AlgoliaClientPort;
import com.backend.api.domain.port.out.ArticleRepositoryPort;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SynchronizeArticleUseCaseImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SynchronizeArticleUseCaseImplTest {
    @MockBean
    private AlgoliaClientPort algoliaClientPort;

    @MockBean
    private ArticleRepositoryPort articleRepositoryPort;

    @Autowired
    private SynchronizeArticleUseCaseImpl synchronizeArticleUseCaseImpl;

    @Test
    void testSynchronize() {
        // Arrange
        AlgoliaResponse.AlgoliaResponseBuilder builderResult = AlgoliaResponse.builder();
        AlgoliaResponse buildResult = builderResult.hits(new ArrayList<>())
                .hitsPerPage(1)
                .nbHits(1)
                .nbPages(1)
                .page(1)
                .query("Query")
                .build();
        when(algoliaClientPort.searchArticleByDate(any(), anyInt(), anyInt())).thenReturn(buildResult);
        doNothing().when(articleRepositoryPort).validateEndInsertRecords(any());

        synchronizeArticleUseCaseImpl.synchronize();

        verify(algoliaClientPort).searchArticleByDate(any(), eq(0), eq(100));
        verify(articleRepositoryPort).validateEndInsertRecords(any());
    }
}
