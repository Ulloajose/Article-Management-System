package com.backend.api.infrastructure.controller;

import com.backend.api.application.service.ArticleService;
import com.backend.api.domain.dto.ArticleDto;
import com.backend.api.domain.dto.FilterArticleDto;
import com.backend.api.domain.model.DataTablesResponse;
import com.backend.api.domain.model.GenericResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Month;

import static org.springframework.http.HttpStatus.OK;

@Tag(name = "Article", description = "Article management APIs")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @Operation(
            summary = "Retrieve an article by filters",
            description = "Get a list of Article per filter set. The response is an Article object with id, author, comment, title and another fields.",
            tags = { "Article", "get" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/articles")
    @ResponseStatus(OK)
    public GenericResponse<DataTablesResponse<ArticleDto>> findAll(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id,asc") String[] sort,
            @RequestParam(defaultValue = "") String tag,
            @RequestParam(defaultValue = "") String author,
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "") Month month) {

        return articleService.findAll(pageNumber, pageSize, sort, FilterArticleDto.builder()
                .tag(tag)
                .month(month)
                .title(title)
                .author(author)
                .build());
    }

    @Operation(
            summary = "Delete a Article by ObjectId",
            description = "Delete a Article by ObjectId. This service change Article status to deleted.",
            tags = { "Article", "delete" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping("/article/{objectId}")
    @ResponseStatus(OK)
    public GenericResponse<Void> deleteTask(@PathVariable String objectId){
        return articleService.delete(objectId);
    }
}
