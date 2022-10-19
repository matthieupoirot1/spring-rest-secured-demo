package com.comvous.unavita.exceptions.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * @author Matthieu POIROT
 * Created only to delegate security access denied exceptions to GlobalExceptionHandler
 * Had to be specified in config with .accessDeniedHandler(customAccessDeniedHandler())
 *
 * @see com.comvous.unavita.security.MyWebSecurityConfiguration
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    HandlerExceptionResolver resolver;

    @Autowired
    public CustomAccessDeniedHandler(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void handle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, org.springframework.security.access.AccessDeniedException accessDeniedException) throws java.io.IOException, javax.servlet.ServletException {
        resolver.resolveException(request, response, null, accessDeniedException);
    }
}
