package com.comvous.unavita.controllers;

import com.comvous.unavita.data.models.User;
import com.comvous.unavita.data.repositories.UserRepository;
import com.comvous.unavita.exceptions.domain.ResourceNotFoundException;
import com.comvous.unavita.exceptions.domain.UniquenessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class CustomerController {

    PasswordEncoder passwordEncoder;
    UserRepository userRepository;

    @Autowired
    public CustomerController(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/customers")
    public List<User> getAllCustomers() {
        return userRepository.findAll();
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<User> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cet utilisateur n'existe pas !")
        ));
    }

    @PostMapping("/customers")
    public ResponseEntity<User> createCustomer(@Validated @RequestBody User user) {
        if(userRepository.findByEmail(user.getEmail()) != null) {
            throw new UniquenessException("Cet utilisateur existe déjà !");
        }

        //encode password using security context encoder
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return ResponseEntity.ok().body(userRepository.save(user));
    }

    @PatchMapping("/customers/{id}")
    public ResponseEntity<User> updateCustomer(@PathVariable Long id, @Validated @RequestBody User user) {
        User userToUpdate = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cet utilisateur n'existe pas !")
        );
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setAddress(user.getAddress());
        userToUpdate.setCity(user.getCity());
        userToUpdate.setZip(user.getZip());
        userToUpdate.setPhone(user.getPhone());
        return ResponseEntity.ok().body(userRepository.save(userToUpdate));
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<User> deleteCustomer(@PathVariable Long id) {
        User userToDelete = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cet utilisateur n'existe pas !")
        );
        userRepository.delete(userToDelete);
        return ResponseEntity.ok().body(userToDelete);
    }
}
