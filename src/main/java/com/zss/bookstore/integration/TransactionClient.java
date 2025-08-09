package com.zss.bookstore.integration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * createdBy       romeo
 * createdDate     8/8/2025
 * createdTime     18:54
 * projectName     zss-bookstore
 **/

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionClient {

    private final WebClient transactionWebClient;

    @Value("${transaction.api.token}")
    private String token;

    public Mono<TransactionResponse> processTransaction(TransactionRequest request) {
        log.info("Processing transaction request: {}", request);
        return transactionWebClient.post()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(TransactionResponse.class);
    }
}


