package com.comvous.demo.exceptions.security;

public class JwtSignatureNotMatchingException extends RuntimeException {
    public JwtSignatureNotMatchingException(String message) {
        super(message);
    }
}
