package com.thiago.orderprocessor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Item(
        Integer id,
        Integer itemId,
        String distributionCenter) {
}
