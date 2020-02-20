package com.slowiak.simplebackend.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@ConfigurationProperties("salesforce")
public class SalesforceProperties {
    private String username;
    private String password;
    private String clientId;
    private String clientSecret;
}
