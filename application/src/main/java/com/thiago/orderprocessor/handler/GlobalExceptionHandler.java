package com.thiago.orderprocessor.handler;

import com.thiago.orderprocessor.dto.ErrorResponseDTO;
import com.thiago.orderprocessor.exception.BadRequestException;
import com.thiago.orderprocessor.exception.CustomsNotFoundException;
import com.thiago.orderprocessor.exception.InternalServerErrorException;
import com.thiago.orderprocessor.exception.OrderExistInDbException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDTO> bBadRequestHandlerCustomsException(BadRequestException ex) {
        final var errorResponse = new ErrorResponseDTO(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        
        return ResponseEntity.badRequest().body(errorResponse);
    }
    
    @ExceptionHandler(CustomsNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> notFoundHandlerCustomsException(CustomsNotFoundException ex) {
        final var errorResponse = new ErrorResponseDTO(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponseDTO> internalServerErrorExceptionCustomsException(InternalServerErrorException ex) {
        final var errorResponse = new ErrorResponseDTO(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        
        return ResponseEntity.internalServerError().body(errorResponse);
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> runtimeExceptionCustomsException(RuntimeException ex) {
        final var errorResponse = new ErrorResponseDTO(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        
        return ResponseEntity.internalServerError().body(errorResponse);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> exceptionCustomsException(Exception ex) {
        final var errorResponse = new ErrorResponseDTO(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        
        return ResponseEntity.internalServerError().body(errorResponse);
    }
    
    @ExceptionHandler(OrderExistInDbException.class)
    public ResponseEntity<ErrorResponseDTO> orderExistInDbExceptionCustomsException(OrderExistInDbException ex) {
        final var errorResponse = new ErrorResponseDTO(ex.getMessage(), HttpStatus.CONFLICT.value());
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
    
}
