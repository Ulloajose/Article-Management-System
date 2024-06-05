package com.backend.api.infrastructure.controller;

import com.backend.api.application.service.ArticleService;
import com.backend.api.domain.dto.ArticleDto;
import com.backend.api.domain.dto.FilterArticleDto;
import com.backend.api.domain.model.DataTablesResponse;
import com.backend.api.domain.model.DetailResponse;
import com.backend.api.domain.model.GenericResponse;
import com.backend.api.domain.model.ResultResponse;
import com.backend.api.domain.port.in.DeleteArticleUserCase;
import com.backend.api.domain.port.in.FilterArticleUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class ArticleControllerTest {

    @Test
    void testFindAll() {
        DataTablesResponse<ArticleDto> dataTablesResponse = new DataTablesResponse<>();
        dataTablesResponse.setCurrentPage(1);
        dataTablesResponse.setData(new ArrayList<>());
        dataTablesResponse.setTotalItems(1L);
        dataTablesResponse.setTotalPages(1);
        FilterArticleUseCase filterArticleUseCase = mock(FilterArticleUseCase.class);
        when(filterArticleUseCase.findAll(anyInt(), anyInt(), Mockito.any(), Mockito.any()))
                .thenReturn(dataTablesResponse);

        GenericResponse<DataTablesResponse<ArticleDto>> actualFindAllResult = (new ArticleController(
                new ArticleService(filterArticleUseCase, mock(DeleteArticleUserCase.class)))).findAll(10, 3,
                new String[]{"id"}, "Tag", "JaneDoe", "Dr", Month.JANUARY);

        verify(filterArticleUseCase).findAll(eq(10), eq(3), isA(String[].class), isA(FilterArticleDto.class));
        ResultResponse result = actualFindAllResult.getResult();
        List<DetailResponse> details = result.getDetails();
        assertEquals(1, details.size());
        DetailResponse getResult = details.getFirst();
        assertEquals("200 OK", getResult.getDetail());
        assertEquals("200 OK", getResult.getMessage());
        assertEquals("200", getResult.getInternalCode());
        assertEquals("Internal component details", result.getSource());
        assertEquals(1, actualFindAllResult.getData().getCurrentPage());
    }

    @Test
    void testDeleteTask() {
        DeleteArticleUserCase deleteArticleUserCase = mock(DeleteArticleUserCase.class);
        doNothing().when(deleteArticleUserCase).deleteArticle(Mockito.any());

        GenericResponse<Void> actualDeleteTaskResult = (new ArticleController(
                new ArticleService(mock(FilterArticleUseCase.class), deleteArticleUserCase))).deleteTask("42");

        verify(deleteArticleUserCase).deleteArticle("42");
        ResultResponse result = actualDeleteTaskResult.getResult();
        List<DetailResponse> details = result.getDetails();
        assertEquals(1, details.size());
        DetailResponse getResult = details.getFirst();
        assertEquals("200 OK", getResult.getMessage());
        assertEquals("200", getResult.getInternalCode());
        assertEquals("Internal component details", result.getSource());
        assertEquals("The article have been deleted successfully!", getResult.getDetail());
        assertNull(actualDeleteTaskResult.getData());
    }

    @Test
    void testDeleteTaskAnother() {
        ArticleService articleService = mock(ArticleService.class);
        GenericResponse.GenericResponseBuilder<Void> builderResult = GenericResponse.builder();
        GenericResponse.GenericResponseBuilder<Void> dataResult = builderResult.data(null);
        ResultResponse.ResultResponseBuilder builderResult2 = ResultResponse.builder();
        ResultResponse result = builderResult2.details(new ArrayList<>()).source("Source").build();
        GenericResponse<Void> buildResult = dataResult.result(result).build();
        when(articleService.delete(Mockito.any())).thenReturn(buildResult);

        (new ArticleController(articleService)).deleteTask("42");

        verify(articleService).delete("42");
    }
}
