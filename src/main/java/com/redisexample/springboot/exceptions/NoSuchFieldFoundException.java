package com.redisexample.springboot.exceptions;

public class NoSuchFieldFoundException extends RuntimeException {

    public NoSuchFieldFoundException(String message) {
        super(message);
    }
}
