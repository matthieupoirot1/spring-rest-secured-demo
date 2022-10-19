package com.comvous.unavita.security.jwt;

import com.comvous.unavita.exceptions.security.JwtSignatureNotMatchingException;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtTokenVerifierFilter extends OncePerRequestFilter {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final JwtConfig jwtConfig;

    private final HandlerExceptionResolver resolver;

    @Autowired
    public JwtTokenVerifierFilter( JwtConfig jwtConfig, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtConfig = jwtConfig;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());

        if (authorizationHeader == null || authorizationHeader.isBlank() || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");
            Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(jwtConfig.getSecretKey()).build().parseClaimsJws(token);

            Claims body = jws.getBody();

            String email = body.getSubject();

            List<String> authorities = body.get("authorities", List.class);
            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    simpleGrantedAuthorities
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        }catch(JwtException e){
            resolver.resolveException(request, response, null, e);
        }


    }
}