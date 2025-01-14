package com.shopping_cart.exception;

public class AlreadyExistException extends RuntimeException{
    public AlreadyExistException(String msg) {
        super(msg);
    }
}
