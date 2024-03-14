package com.example.jwtsecond.global.security.handler;

import com.example.jwtsecond.global.util.JWTUtil;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class APILoginSuccessHandler implements AuthenticationSuccessHandler { // 토큰 생성

    private final JWTUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        log.info(" --------------- Login Success Handler ---------------");

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        log.info(authentication);
        log.info(" * authentication - username : "+authentication.getName()); //username


        Map<String, Object> claim = Map.of("mid", authentication.getName());

        // AccessToken expired 1day
        String accessToken = jwtUtil.generateToken(claim, 1);
        log.info(" * accessToken : "+accessToken);

        // RefreshToken expired 30day
        String refreshToken = jwtUtil.generateToken(claim, 30);

        Gson gson = new Gson();

        Map<String, String> keyMap = Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken);

        String jsonStr = gson.toJson(keyMap);
        log.info(" * (SuccessHandler)jsonStr : "+jsonStr);

        response.getWriter().println(jsonStr);


    }
}
