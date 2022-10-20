package com.comvous.unavita.services;

import com.comvous.unavita.controllers.payload.LoginRequest;
import com.comvous.unavita.data.models.User;
import com.comvous.unavita.data.repositories.RoleRepository;
import com.comvous.unavita.data.repositories.UserRepository;
import com.comvous.unavita.exceptions.domain.ResourceNotFoundException;
import com.comvous.unavita.security.CustomUserDetails;
import com.comvous.unavita.security.facade.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //get by id
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cet utilisateur n'existe pas !")
        );
    }

    //get by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    //get all
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //update
    public User updateUser(Long id, User user) {
        User userToUpdate = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cet utilisateur n'existe pas !")
        );
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPhone(user.getPhone());
        userToUpdate.setAddress(user.getAddress());
        userToUpdate.setCity(user.getCity());
        userToUpdate.setZip(user.getZip());
        return userRepository.save(userToUpdate);
    }

    //delete
    public void deleteUser(Long id) {
        User userToDelete = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cet utilisateur n'existe pas !")
        );
        userRepository.delete(userToDelete);
    }
}
