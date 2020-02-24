package com.slowiak.simplebackend.controller;

import com.slowiak.simplebackend.model.OrderItemPcc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import static com.slowiak.simplebackend.config.Profiles.LOCAL;

@Slf4j
@Component
@Profile(LOCAL)
@RequiredArgsConstructor
public class LocalConnector implements Connector {
    @Override
    public Flux<OrderItemPcc> getOrderItemPccByProductName(String productName) {
        log.info("Running with default profile. User DEV or CUSTOM instead.");
        return Flux.empty();
    }
}
