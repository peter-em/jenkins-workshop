package com.slowiak.simplebackend.controller;

import com.slowiak.simplebackend.model.OrderItemPcc;
import reactor.core.publisher.Flux;

public interface Connector {
    Flux<OrderItemPcc> getOrderItemPccByProductName(String productName);
}
