package com.backend.api.domain.model.algolia;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class StoryTitle {

    private String matchLevel;
    private List<String> matchedWords;
    private String value;
}
