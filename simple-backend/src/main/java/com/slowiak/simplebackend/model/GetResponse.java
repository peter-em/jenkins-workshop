package com.slowiak.simplebackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GetResponse<T> {
    @JsonProperty("totalSize")
    private Integer size;
    @JsonProperty("done")
    private Boolean isDone;
    @JsonProperty("records")
    private List<T> results;
}
