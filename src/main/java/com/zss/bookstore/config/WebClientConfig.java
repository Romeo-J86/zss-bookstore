package com.zss.bookstore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * createdBy       romeo
 * createdDate     9/8/2025
 * createdTime     08:24
 * projectName     zss-bookstore
 **/

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient transactionWebClient(@Value("${transaction.api.url}") String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}

