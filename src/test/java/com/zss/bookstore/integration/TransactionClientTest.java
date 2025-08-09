package com.zss.bookstore.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionClientTest {

    @Mock
    private WebClient mockWebClient;

    @Mock
    private WebClient.RequestBodyUriSpec mockRequestBodyUriSpec;

    @Mock
    private WebClient.RequestBodySpec mockRequestBodySpec;

    @Mock
    private WebClient.RequestHeadersSpec<?> mockRequestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec mockResponseSpec;

    @InjectMocks
    private TransactionClient transactionClient;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(transactionClient, "token", "test-token");
    }

    @Test
    void shouldProcessTransaction() {

        //GIVEN
        TransactionRequest request = new TransactionRequest();
        TransactionResponse expectedResponse = new TransactionResponse();
        expectedResponse.setUpdated("2025-08-09");
        expectedResponse.setResponseCode("00");
        expectedResponse.setResponseDescription("Success");
        expectedResponse.setReference("12345");
        expectedResponse.setDebitReference("67890");

        // WHEN
        when(mockWebClient.post()).thenReturn(mockRequestBodyUriSpec);
        when(mockRequestBodyUriSpec.header(anyString(), anyString())).thenReturn(mockRequestBodySpec);
        when(mockRequestBodySpec.bodyValue(any())).thenReturn((WebClient.RequestHeadersSpec)mockRequestHeadersSpec);
        when(mockRequestHeadersSpec.retrieve()).thenReturn(mockResponseSpec);
        when(mockResponseSpec.bodyToMono(TransactionResponse.class)).thenReturn(Mono.just(expectedResponse));

        Mono<TransactionResponse> result = transactionClient.processTransaction(request);

        StepVerifier.create(result)
                .expectNext(expectedResponse)
                .verifyComplete();

        // THEN
        verify(mockWebClient).post();
        verify(mockRequestBodyUriSpec).header(eq(HttpHeaders.AUTHORIZATION), eq("Bearer test-token"));
        verify(mockRequestBodySpec).bodyValue(eq(request));
    }

    @Test
    void shouldReturnCardRejectedResponse() {

        // GIVEN
        TransactionRequest request = new TransactionRequest();
        TransactionRequest.Card card = new TransactionRequest.Card();
        card.setId("1234560000000002");
        request.setCard(card);

        TransactionResponse mockResponse = new TransactionResponse();
        mockResponse.setUpdated("2025-08-09T12:00:00Z");
        mockResponse.setResponseCode("005");
        mockResponse.setResponseDescription("Rejected by mock rule");
        mockResponse.setReference("ref-12345");
        mockResponse.setDebitReference("debit-12345");

        // WHEN
        when(mockWebClient.post()).thenReturn(mockRequestBodyUriSpec);
        when(mockRequestBodyUriSpec.header(anyString(), anyString())).thenReturn(mockRequestBodySpec);
        when(mockRequestBodySpec.bodyValue(any())).thenReturn((WebClient.RequestHeadersSpec)mockRequestHeadersSpec);
        when(mockRequestHeadersSpec.retrieve()).thenReturn(mockResponseSpec);
        when(mockResponseSpec.bodyToMono(TransactionResponse.class)).thenReturn(Mono.just(mockResponse));

        StepVerifier.create(transactionClient.processTransaction(request))
                .assertNext(response -> {
                    assertEquals("005", response.getResponseCode());
                    assertEquals("Rejected by mock rule", response.getResponseDescription());
                })
                .verifyComplete();

        // THEN
        verify(mockWebClient).post();
        verify(mockRequestBodyUriSpec).header(eq(HttpHeaders.AUTHORIZATION), eq("Bearer test-token"));
        verify(mockRequestBodySpec).bodyValue(eq(request));
    }
    @Test
    void shouldThrowExceptionForCardException() {

        // GIVEN
        TransactionRequest request = new TransactionRequest();
        TransactionRequest.Card card = new TransactionRequest.Card();
        card.setId("1234560000000004");
        request.setCard(card);

        // WHEN
        when(mockWebClient.post()).thenReturn(mockRequestBodyUriSpec);
        when(mockRequestBodyUriSpec.header(anyString(), anyString())).thenReturn(mockRequestBodySpec);
        when(mockRequestBodySpec.bodyValue(any())).thenReturn((WebClient.RequestHeadersSpec)mockRequestHeadersSpec);
        when(mockRequestHeadersSpec.retrieve()).thenReturn(mockResponseSpec);
        when(mockResponseSpec.bodyToMono(TransactionResponse.class))
                .thenReturn(Mono.error(new RuntimeException("Mock exception for test card")));

        StepVerifier.create(transactionClient.processTransaction(request))
                .expectErrorMatches(throwable ->
                        throwable instanceof RuntimeException &&
                                throwable.getMessage().equals("Mock exception for test card")
                )
                .verify();

        // THEN
        verify(mockWebClient).post();
        verify(mockRequestBodyUriSpec).header(eq(HttpHeaders.AUTHORIZATION), eq("Bearer test-token"));
        verify(mockRequestBodySpec).bodyValue(eq(request));
    }

    @Test
    void shouldReturnRandomApprovedResponse() {

        // GIVEN
        TransactionRequest request = new TransactionRequest();
        TransactionRequest.Card card = new TransactionRequest.Card();
        card.setId("1234560000000003");
        request.setCard(card);

        TransactionResponse approvedResponse = new TransactionResponse();
        approvedResponse.setUpdated("2025-08-09T12:00:00Z");
        approvedResponse.setResponseCode("000");
        approvedResponse.setResponseDescription("Randomly approved");
        approvedResponse.setReference("ref-random-approved");
        approvedResponse.setDebitReference("debit-random-approved");

        // WHEN
        when(mockWebClient.post()).thenReturn(mockRequestBodyUriSpec);
        when(mockRequestBodyUriSpec.header(anyString(), anyString())).thenReturn(mockRequestBodySpec);
        when(mockRequestBodySpec.bodyValue(any())).thenReturn((WebClient.RequestHeadersSpec)mockRequestHeadersSpec);
        when(mockRequestHeadersSpec.retrieve()).thenReturn(mockResponseSpec);
        when(mockResponseSpec.bodyToMono(TransactionResponse.class)).thenReturn(Mono.just(approvedResponse));

        StepVerifier.create(transactionClient.processTransaction(request))
                .assertNext(response -> {
                    assertEquals("000", response.getResponseCode());
                    assertEquals("Randomly approved", response.getResponseDescription());
                })
                .verifyComplete();

        // THEN
        verify(mockWebClient).post();
        verify(mockRequestBodyUriSpec).header(eq(HttpHeaders.AUTHORIZATION), eq("Bearer test-token"));
        verify(mockRequestBodySpec).bodyValue(eq(request));
    }

    @Test
    void shouldReturnRandomRejectedResponse() {

        // GIVEN
        TransactionRequest request = new TransactionRequest();
        TransactionRequest.Card card = new TransactionRequest.Card();
        card.setId("1234560000000003");
        request.setCard(card);

        TransactionResponse rejectedResponse = new TransactionResponse();
        rejectedResponse.setUpdated("2025-08-09T12:00:00Z");
        rejectedResponse.setResponseCode("005");
        rejectedResponse.setResponseDescription("Randomly rejected");
        rejectedResponse.setReference("ref-random-rejected");
        rejectedResponse.setDebitReference("debit-random-rejected");

        // WHEN
        when(mockWebClient.post()).thenReturn(mockRequestBodyUriSpec);
        when(mockRequestBodyUriSpec.header(anyString(), anyString())).thenReturn(mockRequestBodySpec);
        when(mockRequestBodySpec.bodyValue(any())).thenReturn((WebClient.RequestHeadersSpec)mockRequestHeadersSpec);
        when(mockRequestHeadersSpec.retrieve()).thenReturn(mockResponseSpec);
        when(mockResponseSpec.bodyToMono(TransactionResponse.class)).thenReturn(Mono.just(rejectedResponse));

        StepVerifier.create(transactionClient.processTransaction(request))
                .assertNext(response -> {
                    assertEquals("005", response.getResponseCode());
                    assertEquals("Randomly rejected", response.getResponseDescription());
                })
                .verifyComplete();

        // THEN
        verify(mockWebClient).post();
        verify(mockRequestBodyUriSpec).header(eq(HttpHeaders.AUTHORIZATION), eq("Bearer test-token"));
        verify(mockRequestBodySpec).bodyValue(eq(request));
    }

}
