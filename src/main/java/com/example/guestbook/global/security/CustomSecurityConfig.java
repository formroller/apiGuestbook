package com.example.guestbook.global.security;

import com.example.guestbook.global.security.handler.CustomSocialLoginSuccessHandler;
import com.example.guestbook.global.security.service.CustomDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity

public class CustomSecurityConfig {
    private final DataSource dataSource;
    private final CustomDetailsService customDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        log.info("====== Security Config ========");

        http.formLogin(withDefaults());
        http.csrf(csrf->csrf.disable());

        http.rememberMe(remember ->{
            remember.key("12345")
                    .tokenRepository(persistentTokenRepository())
                    .userDetailsService(customDetailsService)
                    .tokenValiditySeconds(60 * 60 * 24 * 30);
    });

//        http.oauth2Login(Customizer.withDefaults());
        http.oauth2Login(withDefaults());
        http.oauth2Login(auth -> auth.successHandler(authenticationSuccessHandler()));


        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        // 정적 자원 처리
        log.info(" ======== web configure =========");

        return (web)-> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new CustomSocialLoginSuccessHandler(passwordEncoder());
    }

}
