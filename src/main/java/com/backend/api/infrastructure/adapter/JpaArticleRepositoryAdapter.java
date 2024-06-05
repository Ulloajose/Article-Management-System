package com.backend.api.infrastructure.adapter;

import com.backend.api.domain.dto.ArticleDto;
import com.backend.api.domain.dto.FilterArticleDto;
import com.backend.api.domain.exeption.NotFoundException;
import com.backend.api.domain.mapper.ArticleMapper;
import com.backend.api.domain.model.DataTablesResponse;
import com.backend.api.domain.model.algolia.Hit;
import com.backend.api.domain.port.out.ArticleRepositoryPort;
import com.backend.api.domain.util.Constant;
import com.backend.api.domain.util.PaginationUtil;
import com.backend.api.infrastructure.entity.ArticleEntity;
import com.backend.api.infrastructure.repository.ArticleRepository;
import com.backend.api.infrastructure.repository.specification.ArticleSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JpaArticleRepositoryAdapter implements ArticleRepositoryPort {

    private final ArticleRepository articleRepository;


    @Transactional
    @Override
    public void validateEndInsertRecords(List<Hit> hits) {
        Set<String> objectIds = hits.stream()
                .map(Hit::getObjectID)
                .collect(Collectors.toSet());

        log.info("Filter exist article.");
        Set<String> objectIdsExist = articleRepository.findAllByObjectIdIn(objectIds)
                .stream()
                .map(ArticleEntity::getObjectId)
                .collect(Collectors.toSet());

        log.info("Saving article.");
        Set<ArticleEntity> noExist = hits.stream()
                .collect(Collectors.groupingBy(Hit::getObjectID))
                .values().stream()
                .map(List::getFirst)
                .filter(row -> !objectIdsExist.contains(row.getObjectID()))
                .map(ArticleMapper::map)
                .collect(Collectors.toSet());
        articleRepository.saveAll(noExist);
    }

    @Override
    public void deleteArticle(String objectId, Long userId) {
        ArticleEntity articleEntity = articleRepository.findByObjectId(objectId)
                .orElseThrow(() -> new NotFoundException(Constant.ARTICLE_NOT_FOUND));
        articleEntity.setDeleted(true);
        articleEntity.setCreatedBy(userId);
        articleEntity.setLastModifiedBy(userId);
        articleEntity.setLastModifiedDate(LocalDateTime.now());
        articleRepository.save(articleEntity);
    }

    @Override
    public DataTablesResponse<ArticleDto> findAll(int pageNumber, int pageSize, String[] sort, FilterArticleDto filter) {

        Pageable pageable = getPageable(pageNumber, pageSize, sort);

        Specification<ArticleEntity> specNoDeleted = ArticleSpecification.notDeleted();
        Specification<ArticleEntity> specAuthor = ArticleSpecification.authorLike(filter.getAuthor());
        Specification<ArticleEntity> specTitle = ArticleSpecification.titleLike(filter.getTitle());
        Specification<ArticleEntity> specMonth = ArticleSpecification.byMonth(filter.getMonth());
        Specification<ArticleEntity> specTag = ArticleSpecification.inTag(filter.getTag());

        Specification<ArticleEntity> combinedSpec = Specification.where(specAuthor)
                .and(specTitle).and(specMonth).and(specTag).and(specNoDeleted);

        Page<ArticleEntity> entityPage = articleRepository.findAll(combinedSpec, pageable);
        List<ArticleDto> bList = entityPage.stream()
                .map(ArticleMapper::map)
                .toList();

        DataTablesResponse<ArticleDto> tablesResponse = new DataTablesResponse<>();
        tablesResponse.setData(bList);
        tablesResponse.setCurrentPage(entityPage.getNumber());
        tablesResponse.setTotalItems(entityPage.getTotalElements());
        tablesResponse.setTotalPages(entityPage.getTotalPages());

        return tablesResponse;
    }

    private Pageable getPageable(int pageNumber, int pageSize, String[] sort){
        List<Sort.Order> orders = PaginationUtil.setSort(sort);
        return PageRequest.of(pageNumber, pageSize, Sort.by(orders));
    }
}
