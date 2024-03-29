package com.comvous.demo.services;

import com.comvous.demo.controllers.payload.LoginRequest;
import com.comvous.demo.data.models.Role;
import com.comvous.demo.data.models.User;
import com.comvous.demo.data.repositories.RoleRepository;
import com.comvous.demo.data.repositories.UserRepository;
import com.comvous.demo.exceptions.domain.UniquenessException;
import com.comvous.demo.security.CustomUserDetails;
import com.comvous.demo.security.jwt.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    public AuthService(PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserRepository userRepository, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

        public User createUser(User user) {
            //if user already exists throw uniquenessnexception
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new UniquenessException("Cet utilisateur existe déjà !");
            }
            logger.debug("Available roles: " + roleRepository.findAll());
            //encode password using security context encoder
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            //set role default value
            user.setRoles(List.of(roleRepository.findByName(Role.DEFAULT_ROLE)));
            return userRepository.save(user);
        }

        public String login(LoginRequest loginRequest) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return jwtTokenUtil.generateAccessToken(((CustomUserDetails) authentication.getPrincipal()).user());
        }
}