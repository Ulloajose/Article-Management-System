package com.backend.api.application.usercase;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.backend.api.domain.dto.ArticleDto;
import com.backend.api.domain.dto.FilterArticleDto;
import com.backend.api.domain.model.DataTablesResponse;
import com.backend.api.domain.port.out.ArticleRepositoryPort;

import java.time.Month;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {FilterArticleUserCaseImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class FilterArticleUserCaseImplTest {
    @MockBean
    private ArticleRepositoryPort articleRepositoryPort;

    @Autowired
    private FilterArticleUserCaseImpl filterArticleUserCaseImpl;

    @Test
    void testFindAll() {
        // Arrange
        DataTablesResponse<ArticleDto> dataTablesResponse = new DataTablesResponse<>();
        dataTablesResponse.setCurrentPage(1);
        dataTablesResponse.setData(new ArrayList<>());
        dataTablesResponse.setTotalItems(1L);
        dataTablesResponse.setTotalPages(1);
        when(articleRepositoryPort.findAll(anyInt(), anyInt(), Mockito.any(), Mockito.any()))
                .thenReturn(dataTablesResponse);
        int pageNumber = 10;
        int pageSize = 3;
        String[] sort = new String[]{"Sort"};
        FilterArticleDto filterArticleDto = new FilterArticleDto("JaneDoe", "Tag", "Dr", Month.JANUARY);

        DataTablesResponse<ArticleDto> actualFindAllResult = filterArticleUserCaseImpl.findAll(pageNumber, pageSize, sort,
                filterArticleDto);

        verify(articleRepositoryPort).findAll(eq(10), eq(3), isA(String[].class), isA(FilterArticleDto.class));
        assertSame(dataTablesResponse, actualFindAllResult);
    }
}
