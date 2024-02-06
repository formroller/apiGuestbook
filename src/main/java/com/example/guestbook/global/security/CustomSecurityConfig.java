package com.example.guestbook.global.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class CustomSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity sec) throws Exception{
        log.info("====== Security Config ========");

        return sec.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        log.info(" ======== web configure =========");

        return (web)-> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

}
