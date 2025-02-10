package com.thiago.orderprocessor.dto;

import java.util.List;

public record Order(
        Integer id,
        String numberOrder,
        List<Item> items
) {
}
