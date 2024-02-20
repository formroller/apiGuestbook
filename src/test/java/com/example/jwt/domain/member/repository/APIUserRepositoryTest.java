package com.example.jwt.domain.member.repository;

import com.example.jwt.domain.member.entity.APIUser;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class APIUserRepositoryTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private APIUserRepository apiUserRepository;

    @DisplayName("Insert Test")
    @Test
    public void testInsert(){
        IntStream.rangeClosed(1,100).forEach(i->{
            APIUser user = APIUser.builder()
                    .mid("apiuser"+i)
                    .pwd(passwordEncoder.encode("1111"))
                    .build();

            apiUserRepository.save(user);
        });
    }

}