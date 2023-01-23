package com.comvous.demo.controllers;

import com.comvous.demo.controllers.payload.LoginRequest;
import com.comvous.demo.data.models.User;
import com.comvous.demo.data.models.projections.UserDTO;
import com.comvous.demo.security.CustomUserDetails;
import com.comvous.demo.services.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthController(AuthService authService, ModelMapper modelMapper) {
        this.authService = authService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Expose-Headers", "Authorization");
        responseHeaders.set("Authorization", "Bearer " + token);
        UserDTO user = modelMapper.map(((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).user(), UserDTO.class);
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(user);
    }

    @PostMapping("/register")
    public User registerUser(@Validated @RequestBody User user) {
        return authService.createUser(user);
    }
}
