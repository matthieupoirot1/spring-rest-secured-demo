package com.comvous.demo.security.facade;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
}
