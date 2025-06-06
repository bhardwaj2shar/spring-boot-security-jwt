package com.example.securityjwt;

import com.example.securityjwt.filter.JwtAuthenticatonFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private  JwtAuthenticatonFilter jwtAuthenticationFilter;



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception {
        http.cors(AbstractHttpConfigurer::disable)
                        .csrf(AbstractHttpConfigurer::disable).

                authorizeHttpRequests((authz) ->
                authz.requestMatchers("/auth/login").permitAll().
                        requestMatchers("/api/user").hasRole("USER")
                        .requestMatchers("/api/admin").hasRole("ADMIN")
                        .requestMatchers("/api/both").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
