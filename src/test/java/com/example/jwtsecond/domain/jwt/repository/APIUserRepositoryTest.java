package com.example.jwtsecond.domain.jwt.repository;

import com.example.jwtsecond.domain.jwt.entity.APIUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class APIUserRepositoryTest {
    @Autowired
    private APIUserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("Insert Test")
    @Test
    public void testInsert(){
        IntStream.rangeClosed(1,100).forEach(i->{
            APIUser user = APIUser.builder()
                    .mid("user"+i)
                    .pwd(passwordEncoder.encode("1111"))
                    .build();

            repository.save(user);
        });
    }

}