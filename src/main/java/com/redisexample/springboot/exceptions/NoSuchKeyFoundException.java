package com.redisexample.springboot.exceptions;

public class NoSuchKeyFoundException extends RuntimeException {

    public NoSuchKeyFoundException() {

    }

    public NoSuchKeyFoundException(String message) {
        super(message);
    }

}
