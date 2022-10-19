package com.comvous.unavita.security;

import com.comvous.unavita.exceptions.security.CustomAccessDeniedHandler;
import com.comvous.unavita.exceptions.security.DelegatedAuthenticationEntryPoint;
import com.comvous.unavita.security.jwt.JwtTokenVerifierFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
public class MyWebSecurityConfiguration {

    JwtTokenVerifierFilter jwtTokenVerifierFilter;

    UserDetailsService userDetailsService;
    DelegatedAuthenticationEntryPoint delegatedAuthenticationEntryPoint;
    CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    public MyWebSecurityConfiguration(JwtTokenVerifierFilter jwtTokenVerifierFilter, UserDetailsService userDetailsService, DelegatedAuthenticationEntryPoint delegatedAuthenticationEntryPoint, CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.jwtTokenVerifierFilter = jwtTokenVerifierFilter;
        this.userDetailsService = userDetailsService;
        this.delegatedAuthenticationEntryPoint = delegatedAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/**")
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(delegatedAuthenticationEntryPoint);

        http.addFilterBefore(jwtTokenVerifierFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*@Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.eraseCredentials(false)
                .userDetailsService()
                .passwordEncoder(passwordEncoder);

        return authenticationManagerBuilder.build();
    }*/

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}