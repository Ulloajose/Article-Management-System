package com.backend.api.infrastructure.adapter;

import com.backend.api.domain.model.algolia.AlgoliaResponse;
import com.backend.api.domain.port.out.AlgoliaClientPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Component
public class AlgoliaClientPortImpl implements AlgoliaClientPort {

    @Value("${app.algolia_api.host}")
    private String host;

    @Override
    public AlgoliaResponse searchArticleByDate(String query, int page, int hitsPerPage) {
        return builderDefaultConfiguration(host)
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search_by_date")
                        .queryParam("query", query)
                        .queryParam("page", page)
                        .queryParam("hitsPerPage", hitsPerPage)
                        .build())
                .retrieve()
                .bodyToMono(AlgoliaResponse.class)
                .block();
    }

    private WebClient.Builder builderDefaultConfiguration(String host){
        return WebClient.builder()
                .clientConnector(connector())
                .baseUrl(host)
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

    private ClientHttpConnector connector() {
        return new
                ReactorClientHttpConnector(HttpClient.create(ConnectionProvider.newConnection()));
    }
}
