package com.slowiak.simplebackend.controller;

import com.slowiak.simplebackend.config.SalesforceProperties;
import com.slowiak.simplebackend.model.GetResponse;
import com.slowiak.simplebackend.model.OrderItemPcc;
import com.slowiak.simplebackend.model.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static com.slowiak.simplebackend.config.Profiles.*;
import static com.slowiak.simplebackend.config.Profiles.DEV;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.web.reactive.function.BodyInserters.fromMultipartData;

@Component
@Profile({CUSTOM, DEV})
@RequiredArgsConstructor
public class SalesforceConnector implements Connector{
    private final WebClient webClient;
    private final SalesforceProperties salesforceProperties;

    @Override
    public Flux<OrderItemPcc> getOrderItemPccByProductName(String productName) {
        var query = "SELECT+Id,Sabre_PCC_Text__c,Sabre_Product__c+FROM+ccSabre_OrderItem_PCC__c+WHERE+Sabre_Product__c='" + productName + "'";
        return getToken().flatMapMany(token ->
                webClient.get()
                        .uri("/data/v46.0/query/?q=" + query)
                        .header(AUTHORIZATION, "Bearer " + token)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<GetResponse<OrderItemPcc>>() {
                        })
                        .map(GetResponse::getResults)
                        .flatMapIterable(Function.identity())
        );
    }

    private Mono<String> getToken() {
        return webClient.post()
                .uri("/oauth2/token")
                .accept(MediaType.APPLICATION_JSON)
                .body(
                        fromMultipartData("username", salesforceProperties.getUsername())
                                .with("password", salesforceProperties.getPassword())
                                .with("grant_type", "password")
                                .with("client_id", salesforceProperties.getClientId())
                                .with("client_secret", salesforceProperties.getClientSecret())
                )
                .retrieve()
                .bodyToMono(Token.class)
                .map(Token::getToken)
                .cache();
    }
}
