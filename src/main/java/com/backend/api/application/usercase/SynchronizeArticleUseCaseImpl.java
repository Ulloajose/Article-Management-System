package com.backend.api.application.usercase;

import com.backend.api.domain.model.algolia.AlgoliaResponse;
import com.backend.api.domain.model.algolia.Hit;
import com.backend.api.domain.port.in.SynchronizeArticleUseCase;
import com.backend.api.domain.port.out.AlgoliaClientPort;
import com.backend.api.domain.port.out.ArticleRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SynchronizeArticleUseCaseImpl implements SynchronizeArticleUseCase {

    private final AlgoliaClientPort algoliaClientPort;
    private final ArticleRepositoryPort articleRepositoryPort;

    @Override
    public void synchronize() {
        log.info("Starting synchronization process with Algolia.");
        articleRepositoryPort.validateEndInsertRecords(getAllHit());
        log.info("Ended synchronization process with Algolia.");
    }

    private List<Hit> getAllHit(){
        List<Hit> hitList = new ArrayList<>();
        int page = 0;

        while (true){
            log.info("Getting the article in Algolia, pages {}", page);

            AlgoliaResponse response = algoliaClientPort
                    .searchArticleByDate("java", page, 100);
            page += 1;
            if (response.getHits().isEmpty())
                break;
            hitList.addAll(response.getHits());
        }
        return hitList;
    }
}
