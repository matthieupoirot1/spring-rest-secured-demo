package com.comvous.demo.security.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpHeaders;

import java.security.Key;


/**
 * @author Matthieu POIROT
 * Annoted with @ConfigurationProperties to read the properties from the application.yml file
 * Annoted with @ConstructorBinding automatically bind the properties to the constructor
 * Annoted with @EnableConfigurationProperties to enable the configuration properties
 * Care implement following properties :
 * - application.jwt.tokenprefix
 * - application.jwt.tokenexpirationafterdays
 * - application.jwt.issuer
 */
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "application.jwt")
@ConstructorBinding
public class JwtConfig {
    private final Key secretKey;
    /**
     * The auth token prefix
     */
    private final String tokenPrefix;
    /**
     * The auth token issuer
     */
    private final String tokenIssuer;
    /**
     * The auth token number of days before expiration
     */
    private final Integer tokenExpirationAfterDays;
    public JwtConfig(String tokenPrefix, String tokenIssuer, Integer tokenExpirationAfterDays) {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        this.tokenPrefix = tokenPrefix;
        this.tokenExpirationAfterDays = tokenExpirationAfterDays;
        this.tokenIssuer = tokenIssuer;
    }
    public Key getSecretKey() {
        return secretKey;
    }
    public String getTokenPrefix() {
        return tokenPrefix;
    }
    public Integer getTokenExpirationAfterDays() {
        return tokenExpirationAfterDays;
    }
    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }
    public String getTokenIssuer() {
        return tokenIssuer;
    }
}