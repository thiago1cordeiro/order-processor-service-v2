package com.thiago.orderprocessor.dto;

public record ErrorResponseDTO(
        String message,
        Integer status
) {
}
