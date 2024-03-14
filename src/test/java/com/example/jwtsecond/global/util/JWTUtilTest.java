package com.example.jwtsecond.global.util;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class JWTUtilTest {
    @Autowired
    private JWTUtil jwtUtil;

    @DisplayName("비밀키 확인")
    @Test
    public void testGenerate(){
        Map<String, Object> claimMap = Map.of("mid", "ABC");

        String jwtStr = jwtUtil.generateToken(claimMap, 1);

        log.info(jwtStr);
    }


    @DisplayName("validToken certified")
    @Test
    public void testValidate(){
        // 유효시간 만료 토큰
        String jwtStr = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDkxMjkzMDMsIm1pZCI6IkFCQyIsImlhdCI6MTcwOTEyOTI0M30.Dx_5Xhatg0xH8J8_Cwu_bsnKyO6xZvXENiuj36xOj1o";

        Map<String, Object> claim = jwtUtil.validateToken(jwtStr);

        log.info(claim);
    }

    @DisplayName("ValidToken Days")
    @Test
    public void testAll(){

        String jwtStr = jwtUtil.generateToken(Map.of("mid","AAAA", "email","abc@aa.com"),1);

        log.info(jwtStr);

        Map<String, Object> claims = jwtUtil.validateToken(jwtStr);

        log.info("MID : "+claims.get("mid"));

        log.info("EMAIL : "+claims.get("email"));
    }

}