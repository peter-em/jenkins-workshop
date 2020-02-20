package com.slowiak.simplebackend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final SalesforceProperties salesforceProperties;

    @Bean
    WebClient webClient() {
        return WebClient.builder().build();
    }
}
