package com.backend.api.domain.port.in;

import com.backend.api.domain.dto.ArticleDto;
import com.backend.api.domain.dto.FilterArticleDto;
import com.backend.api.domain.model.DataTablesResponse;

public interface FilterArticleUseCase {

    DataTablesResponse<ArticleDto> findAll(
            int pageNumber, int pageSize, String[] sort, FilterArticleDto filterArticleDto);
}
