package com.backend.api.domain.model.algolia;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class Hit {

    @JsonProperty("_tags")
    private List<String> tags;

    @JsonProperty("comment_text")
    private String commentText;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("created_at_i")
    private int createdAtIc;

    @JsonProperty("parent_id")
    private int parentId;

    @JsonProperty("story_id")
    private int storyId;

    @JsonProperty("story_title")
    private String storyTitle;

    @JsonProperty("story_url")
    private String storyUrl;

    @JsonProperty("updated_at")
    private String updatedAt;

    private String author;
    private String objectID;
}
