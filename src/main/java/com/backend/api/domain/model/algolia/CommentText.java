package com.backend.api.domain.model.algolia;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommentText {

    private boolean fullyHighlighted;
    private String matchLevel;
    private List<String> matchedWords;
    private String value;
}
