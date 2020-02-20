package com.slowiak.simplebackend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class SimpleBackendApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SimpleBackendApplication.class, args);
        SalesforceConnector salesforceConnector = context.getBean(SalesforceConnector.class);

        String productName = context.getEnvironment().getProperty("productName");
        salesforceConnector.getOrderItemPccByProductName(productName)
                .doOnNext(orderItemPcc -> log.info(orderItemPcc.getId() + " : " + orderItemPcc.getProductName() + " -> " + orderItemPcc.getPcc()))
                .blockFirst();

        context.close();
    }

}
