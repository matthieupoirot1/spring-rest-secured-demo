package com.comvous.demo.controllers;

import com.comvous.demo.controllers.payload.LoginRequest;
import com.comvous.demo.data.models.User;
import com.comvous.demo.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public User registerUser(@Validated @RequestBody User user) {
        return authService.createUser(user);
    }
}
