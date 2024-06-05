package com.backend.api.domain.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Month;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class FilterArticleDto {

    private String author;
    private String tag;
    private String title;
    private Month month;
}
