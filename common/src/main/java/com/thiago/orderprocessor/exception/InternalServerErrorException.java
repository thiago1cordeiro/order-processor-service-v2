package com.thiago.orderprocessor.exception;

public class InternalServerErrorException extends RuntimeException{
    
    public InternalServerErrorException(final String message){
        super(message);
    }
    
}
