package com.comvous.unavita.exceptions.domain;

public class UniquenessException extends RuntimeException {
    public UniquenessException(String message) {
        super(message);
    }
}
