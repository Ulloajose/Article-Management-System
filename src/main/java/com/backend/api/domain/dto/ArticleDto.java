package com.backend.api.domain.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ArticleDto {

    private String author;
    private String comment;
    private String title;
    private String url;
    private List<String> tags;
    private String createDate;
    private String objectId;
}
