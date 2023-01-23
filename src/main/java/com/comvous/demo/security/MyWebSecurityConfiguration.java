package com.comvous.demo.security;

import com.comvous.demo.exceptions.security.CustomAccessDeniedHandler;
import com.comvous.demo.exceptions.security.DelegatedAuthenticationEntryPoint;
import com.comvous.demo.security.jwt.JwtTokenVerifierFilter;
import org.modelmapper.ModelMapper;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
public class MyWebSecurityConfiguration {

    JwtTokenVerifierFilter jwtTokenVerifierFilter;

    DelegatedAuthenticationEntryPoint delegatedAuthenticationEntryPoint;
    CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    public MyWebSecurityConfiguration(JwtTokenVerifierFilter jwtTokenVerifierFilter, DelegatedAuthenticationEntryPoint delegatedAuthenticationEntryPoint, CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.jwtTokenVerifierFilter = jwtTokenVerifierFilter;
        this.delegatedAuthenticationEntryPoint = delegatedAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // if Spring MVC is on classpath and no CorsConfigurationSource is provided,
                // Spring Security will use CORS configuration provided to Spring MVC
                .cors().and().csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/**")
                .permitAll()
                .and()
                .httpBasic()
                .authenticationEntryPoint(delegatedAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);

        http.addFilterBefore(jwtTokenVerifierFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* @Bean
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

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedOrigins(List.of("*"));
        config.addAllowedHeader("Authorization");
        config.addAllowedHeader("content-type");
        config.addAllowedHeader("Access-Control-Allow-Origin");
        config.addAllowedHeader("Access-Control-Allow-Headers");
        config.addAllowedHeader("Access-Control-Allow-Methods");
        config.addAllowedHeader("Access-Control-Request-Headers");
        config.addAllowedHeader("Access-Control-Request-Method");
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}