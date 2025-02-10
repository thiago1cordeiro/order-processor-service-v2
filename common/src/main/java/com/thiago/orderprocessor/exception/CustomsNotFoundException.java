package com.thiago.orderprocessor.exception;

public class CustomsNotFoundException extends RuntimeException{
    
    public CustomsNotFoundException(final String message){
        super(message);
    }
    
}
