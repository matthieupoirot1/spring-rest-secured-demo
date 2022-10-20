package com.comvous.demo.exceptions.domain;

public class UniquenessException extends RuntimeException {
    public UniquenessException(String message) {
        super(message);
    }
}
