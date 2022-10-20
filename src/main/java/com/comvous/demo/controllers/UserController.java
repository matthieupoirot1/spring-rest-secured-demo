package com.comvous.demo.controllers;

import com.comvous.demo.data.models.User;
import com.comvous.demo.security.facade.AuthenticationFacade;
import com.comvous.demo.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class UserController {

    UserService userService;
    AuthenticationFacade authenticationFacade;
    @Autowired
    public UserController(UserService userService, AuthenticationFacade authenticationFacade) {
        this.userService = userService;
        this.authenticationFacade = authenticationFacade;
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')" + "OR principal.user.id == #id")
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')" + "OR principal.user.id == #id")
    @PatchMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @Validated @RequestBody User user) {
        //todo restrict what can be updated
        return userService.updateUser(id, user);
    }


    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
