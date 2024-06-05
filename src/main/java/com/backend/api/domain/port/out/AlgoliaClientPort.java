package com.backend.api.domain.port.out;

import com.backend.api.domain.model.algolia.AlgoliaResponse;

public interface AlgoliaClientPort {

    AlgoliaResponse searchArticleByDate(String query, int page, int hitsPerPage);
}
