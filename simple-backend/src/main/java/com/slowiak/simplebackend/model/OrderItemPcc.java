package com.slowiak.simplebackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderItemPcc {
    @JsonProperty("Id")
    private String id;
    @JsonProperty("Sabre_PCC_Text__c")
    private String pcc;
    @JsonProperty("Sabre_Product__c")
    private String productName;
}
