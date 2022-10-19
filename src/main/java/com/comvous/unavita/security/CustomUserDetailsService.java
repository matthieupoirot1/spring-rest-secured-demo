package com.comvous.unavita.security;

import com.comvous.unavita.data.models.User;
import com.comvous.unavita.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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