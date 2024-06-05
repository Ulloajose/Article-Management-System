package com.backend.api.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.backend.api.domain.dto.ArticleDto;
import com.backend.api.domain.dto.FilterArticleDto;
import com.backend.api.domain.model.DataTablesResponse;
import com.backend.api.domain.model.DetailResponse;
import com.backend.api.domain.model.GenericResponse;
import com.backend.api.domain.model.ResultResponse;
import com.backend.api.domain.port.in.DeleteArticleUserCase;
import com.backend.api.domain.port.in.FilterArticleUseCase;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ArticleService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ArticleServiceTest {
    @Autowired
    private ArticleService articleService;

    @MockBean
    private DeleteArticleUserCase deleteArticleUserCase;

    @MockBean
    private FilterArticleUseCase filterArticleUseCase;

    @Test
    void testFindAll() {
        DataTablesResponse<ArticleDto> dataTablesResponse = new DataTablesResponse<>();
        dataTablesResponse.setCurrentPage(1);
        dataTablesResponse.setData(new ArrayList<>());
        dataTablesResponse.setTotalItems(1L);
        dataTablesResponse.setTotalPages(1);
        when(filterArticleUseCase.findAll(anyInt(), anyInt(), Mockito.<String[]>any(), Mockito.<FilterArticleDto>any()))
                .thenReturn(dataTablesResponse);
        int pageNumber = 10;
        int pageSize = 3;
        String[] sort = new String[]{"Sort"};
        FilterArticleDto filterArticleDto = new FilterArticleDto("JaneDoe", "Tag", "Dr", Month.JANUARY);

        GenericResponse<DataTablesResponse<ArticleDto>> actualFindAllResult = articleService.findAll(pageNumber, pageSize,
                sort, filterArticleDto);

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
    void testDelete() {
        doNothing().when(deleteArticleUserCase).deleteArticle(Mockito.<String>any());
        String objectId = "42";
        GenericResponse<Void> actualDeleteResult = articleService.delete(objectId);

        verify(deleteArticleUserCase).deleteArticle(objectId);
        ResultResponse result = actualDeleteResult.getResult();
        List<DetailResponse> details = result.getDetails();
        assertEquals(1, details.size());
        DetailResponse getResult = details.getFirst();
        assertEquals("200 OK", getResult.getMessage());
        assertEquals("200", getResult.getInternalCode());
        assertEquals("Internal component details", result.getSource());
        assertEquals("The article have been deleted successfully!", getResult.getDetail());
        assertNull(actualDeleteResult.getData());
    }
}
