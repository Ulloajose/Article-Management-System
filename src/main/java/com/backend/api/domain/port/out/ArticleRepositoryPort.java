package com.backend.api.domain.port.out;

import com.backend.api.domain.dto.ArticleDto;
import com.backend.api.domain.dto.FilterArticleDto;
import com.backend.api.domain.model.DataTablesResponse;
import com.backend.api.domain.model.algolia.Hit;

import java.util.List;

public interface ArticleRepositoryPort {
    void validateEndInsertRecords(List<Hit> hits);
    void deleteArticle(String objectId, Long userId);
    DataTablesResponse<ArticleDto> findAll(
            int pageNumber, int pageSize, String[] sort, FilterArticleDto filterArticleDto);

}
