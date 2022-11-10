package com.comvous.demo.security;

import com.comvous.demo.data.models.User;
import com.comvous.demo.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * CustomUserDetailsService is a service that implements the UserDetailsService interface.
 * It is used to load the user details from the database.
 * It is used by the base authenticationProvider : DaoAuthenticationProvider
 * As the class is annoted with @Service, it is automatically registered in the Spring Security context.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    UserRepository userRepository;

    public CustomUserDetailsService() {
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        final User user = userRepository.findByEmail(mail);
        if (user == null) {
            throw new UsernameNotFoundException(mail);
        }
        return new CustomUserDetails(user);
    }

    @Autowired
    private void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}