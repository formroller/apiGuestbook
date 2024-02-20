package com.example.jwt.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Log4j2
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class CustomSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        log.info(" --------- web configure -------");

        return (web) -> web.ignoring()
                .requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception{
        log.info(" ============== configure ===========");

        // csrf 토큰 비활성화
        http.csrf(csrf -> csrf.disable());

        // 세션 사용하지 않음
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return http.build();
    }
}
