package com.backend.api.domain.model.algolia;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlgoliaResponse {

    private List<Hit> hits;
    private int hitsPerPage;
    private int nbHits;
    private int nbPages;
    private int page;
    private String query;
}
