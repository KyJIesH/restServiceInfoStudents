package ru.kuleshov.restserviceinfostudents.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers("/students/**")
                )
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/login").permitAll();
                    auth.anyRequest().authenticated();
                })
                .csrf(withDefaults())
                .oauth2Login(oAuth2Login -> {
                    oAuth2Login.successHandler((request, response, authentication) ->
                            response.sendRedirect("/success"));
                })
                .oauth2Login(withDefaults())
                .logout(withDefaults())
                .build();
    }
}
