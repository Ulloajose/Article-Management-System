package com.backend.api.application.service;

import com.backend.api.domain.dto.ArticleDto;
import com.backend.api.domain.dto.FilterArticleDto;
import com.backend.api.domain.mapper.GenericResponseMapper;
import com.backend.api.domain.model.DataTablesResponse;
import com.backend.api.domain.model.GenericResponse;
import com.backend.api.domain.port.in.DeleteArticleUserCase;
import com.backend.api.domain.port.in.FilterArticleUseCase;
import com.backend.api.domain.util.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final FilterArticleUseCase filterArticleUseCase;
    private final DeleteArticleUserCase deleteArticleUserCase;

    public GenericResponse<DataTablesResponse<ArticleDto>> findAll(
            int pageNumber, int pageSize, String[] sort, FilterArticleDto filterArticleDto){

        DataTablesResponse<ArticleDto> response = filterArticleUseCase
                .findAll(pageNumber, pageSize, sort, filterArticleDto);
        return GenericResponseMapper.buildGenericResponse(response, HttpStatus.OK, HttpStatus.OK.toString());
    }

    public GenericResponse<Void> delete(String objectId){
        deleteArticleUserCase.deleteArticle(objectId);
        return GenericResponseMapper.buildGenericResponse(HttpStatus.OK, Constant.ARTICLE_DELETED_SUCCESS);
    }
}
