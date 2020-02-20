package com.slowiak.simplebackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Token {
    @JsonProperty("access_token")
    private String token;
    @JsonProperty("instance_url")
    private String instanceUrl;
}
