package com.example.jwtsecond.global.security.filter;

import com.example.jwtsecond.global.exception.RefreshTokenException;
import com.example.jwtsecond.global.util.JWTUtil;
import com.google.gson.Gson;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class RefreshTokenFilter  extends OncePerRequestFilter {

    private final String refreshPath;

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        if(!path.equals(refreshPath)){
            log.info("Skip refresh Token Filter");

            filterChain.doFilter(request, response);

            return;
        }

        log.info("Refresh Token Filter ..... Run .....");

        // 전송된 JSON에서 accessToken과 refreshToken 얻어오기
        Map<String, String> tokens = parseRequestJSON(request);

        String accessToken = tokens.get("accessToken");
        String refreshToken = tokens.get("refreshToken");

        log.info("** (RefreshFilter) accessToken : "+accessToken);
        log.info("** (RefreshFilter) refreshToken : "+refreshToken);

        // 토큰 유효성 검증
        try{
            checkAccessToken(accessToken);
        }catch(RefreshTokenException refreshTokenException){
            refreshTokenException.sendResponseError(response);
        }

        Map<String, Object> refreshClaims = null;

        // 토큰 검증 후 refreshToken 새로 발행
        // 만료일 얼마 남지 않은 경우 새로 발급
        try{
            refreshClaims = checkRefreshToken(refreshToken);
            log.info("** RefreshClaims : "+refreshClaims);

            // Refresh Token 만료시간 얼마 남지 않은 경우
            Integer exp = (Integer) refreshClaims.get("exp");

            Date expTime = new Date(Instant.ofEpochMilli(exp).toEpochMilli()*1000);

            Date current = new Date(System.currentTimeMillis());


            // 만료 시간과 현재 시간 간격 계산
            // 3일 미만 남을 경우 refreshToken 재생성
            long gapTime = (expTime.getTime() - current.getTime());

            log.info("------------------------------");
            log.info("current : "+current);
            log.info("expTime : "+expTime);
            log.info("gap : "+gapTime);

            String mid = (String)refreshClaims.get("mid");

            // 해당 단계는 AccessToken 생성
            String accessTokenValue = jwtUtil.generateToken(Map.of("mid", mid), 1);
            String refreshTokenValue = tokens.get("refreshToken");

            // Refresh Token 3일 미만일 경우
            if(gapTime < (1000 * 60 * 60 * 24 * 3)){
                log.info(" ----- New Refresh Token Required -----");
                refreshTokenValue = jwtUtil.generateToken(Map.of("mid",mid),30);
            }
            log.info(" -------- Refresh Token Result -------");
            log.info("accessToken :" +accessTokenValue);
            log.info("refreshToken : "+refreshTokenValue);

            sendTokens(accessTokenValue, refreshTokenValue, response);

        }catch(RefreshTokenException refreshTokenException){
            refreshTokenException.sendResponseError(response);
            return;
        }
    }

    /*Json 데이터를 map 처리*/
    private Map<String, String> parseRequestJSON(HttpServletRequest request) {
        // Json 데이터 분석해 mid, pwd 값을 map 처리
        try(Reader reader = new InputStreamReader(request.getInputStream())){
            Gson gson = new Gson();

            return gson.fromJson(reader, Map.class);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    /*AccessToken 검증*/
    private void checkAccessToken(String accessToken) throws RefreshTokenException{
        try{
            jwtUtil.validateToken(accessToken);
        }catch (ExpiredJwtException expiredJwtException){
            log.info("Access Token has expired");
        }catch (Exception exception){
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.NO_ACCESS);
        }
    }

    /*RefreshToken 검증*/
    private Map<String, Object> checkRefreshToken(String refreshToken)throws RefreshTokenException{
        try {
            Map<String, Object> values = jwtUtil.validateToken(refreshToken);

            return values;

        }catch(ExpiredJwtException expiredJwtException){
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.OLD_REFRESH);
        }catch(Exception exception){
            exception.printStackTrace();
            new RefreshTokenException(RefreshTokenException.ErrorCase.NO_REFRESH);
        }
        return null;
    }


    /*최종 생성된 토큰 전송*/
    private void sendTokens(String accessTokenValue, String refreshTokenValue, HttpServletResponse response){
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Gson gson = new Gson();

        String jsonStr = gson.toJson(Map.of("accessToken", accessTokenValue, "refreshToken",refreshTokenValue));

        try{
            response.getWriter().println(jsonStr);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
