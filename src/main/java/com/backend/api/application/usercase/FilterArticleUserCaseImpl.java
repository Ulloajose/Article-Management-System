package com.backend.api.application.usercase;

import com.backend.api.domain.dto.ArticleDto;
import com.backend.api.domain.dto.FilterArticleDto;
import com.backend.api.domain.model.DataTablesResponse;
import com.backend.api.domain.port.in.FilterArticleUseCase;
import com.backend.api.domain.port.out.ArticleRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FilterArticleUserCaseImpl implements FilterArticleUseCase {

    private final ArticleRepositoryPort articleRepositoryPort;


    @Override
    public DataTablesResponse<ArticleDto> findAll(int pageNumber, int pageSize, String[] sort, FilterArticleDto filterArticleDto) {
        return articleRepositoryPort.findAll(pageNumber, pageSize, sort, filterArticleDto);
    }
}
