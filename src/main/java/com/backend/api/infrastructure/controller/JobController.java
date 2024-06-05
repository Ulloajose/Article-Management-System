package com.backend.api.infrastructure.controller;

import com.backend.api.domain.mapper.GenericResponseMapper;
import com.backend.api.domain.model.GenericResponse;
import com.backend.api.domain.port.in.SynchronizeArticleUseCase;
import com.backend.api.domain.util.Constant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

import static org.springframework.http.HttpStatus.OK;

@Tag(name = "Jobs", description = "Jobs management APIs")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class JobController {

    private final SynchronizeArticleUseCase synchronizeArticleUseCase;

    @Operation(
            summary = "Start job for synchronize article",
            description = "Service synchronizes all available articles with the Algolia API.",
            tags = { "Jobs", "get" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/job/execute")
    @ResponseStatus(OK)
    public GenericResponse<Void> deleteTask(){

        CompletableFuture.runAsync(synchronizeArticleUseCase::synchronize);
        return GenericResponseMapper.buildGenericResponse(HttpStatus.OK, Constant.ARTICLE_SYNCHRONIZATION_STARTING);
    }
}
