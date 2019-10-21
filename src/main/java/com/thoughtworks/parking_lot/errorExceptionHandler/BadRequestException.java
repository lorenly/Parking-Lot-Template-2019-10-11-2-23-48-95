package com.thoughtworks.parking_lot.errorExceptionHandler;

public class BadRequestException extends Exception {
    public BadRequestException(String msg){
        super(msg);
    }
}
