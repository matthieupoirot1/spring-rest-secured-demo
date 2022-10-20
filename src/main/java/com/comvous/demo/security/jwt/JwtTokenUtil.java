package com.comvous.demo.security.jwt;

import com.comvous.demo.data.models.User;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtTokenUtil {

    private final JwtConfig jwtConfig;

    @Autowired
    public JwtTokenUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String generateAccessToken(User user) {

        List<String> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(role.getName());
        });

        //create claims
        Map<String, List<String>> claims = new HashMap<>();
        claims.put("authorities", authorities);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuer(jwtConfig.getTokenIssuer())
                .setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
                .signWith(jwtConfig.getSecretKey()).compact();
    }

    public String generateRefreshToken(User user) {

        return Jwts.builder()
                .setSubject(String.format("%s,%s", user.getId(), user.getEmail()))
                .setIssuer(jwtConfig.getTokenIssuer())
                .setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
                .signWith(jwtConfig.getSecretKey()).compact();
    }

    public boolean validate(String token) {
        try {
            long expiresAt = Jwts.parserBuilder().setSigningKey(jwtConfig.getSecretKey()).build().parseClaimsJws(token).getBody().getExpiration().getTime();
            Calendar cal = Calendar.getInstance();
            if (expiresAt >= cal.getTime().getTime()) {
                return true;
            }
        } catch (IllegalArgumentException e) {
            System.out.printf("JWT is invalid - {%s}%n", e.getMessage());
        }

        return false;
    }

    public String getMail(String token) {

        return Jwts.parserBuilder().setSigningKey(jwtConfig.getSecretKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

}