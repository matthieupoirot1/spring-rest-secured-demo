package com.comvous.demo.services;

import com.comvous.demo.data.models.User;
import com.comvous.demo.data.models.projections.UserDTO;
import com.comvous.demo.data.models.projections.UserWithReportsDTO;
import com.comvous.demo.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserService extends BaseCrudService<UserRepository, User> {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        super(userRepository);
        this.passwordEncoder = passwordEncoder;
    }

    //get by email
    public User getUserByEmail(String email) {
        return linkedRepository.findByEmail(email);
    }

    public User generateUser(User user) {
        //generate user password and set in user
        user.setPassword(this.passwordEncoder.encode(user.getFirstName().toLowerCase().charAt(0) + user.getLastName().toLowerCase()));
        return linkedRepository.save(user);
    }

    public List<User> getUsersBySemesterName(String name){
        return this.linkedRepository.findAllBySemesterName(name);
    }

    public List<User> getAllTeachers() {
        return this.linkedRepository.findAllByTeacher(true);
    }

}
