package com.thiago.orderprocessor.exception;

public class OrderExistInDbException extends RuntimeException{

    public OrderExistInDbException(final String message){
        super(message);
    }

}
