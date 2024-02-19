package com.example.guestbook.global.security.handler;

import com.example.guestbook.global.security.dto.MemberSecurityDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class CustomSocialLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("-".repeat(40));
        log.info(" ------ CustomLoginSuccessHandler onAuthenticationSuccess --------");
        log.info(authentication.getPrincipal());

        MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) authentication.getPrincipal();
        String encodePw = memberSecurityDTO.getPwd();

        // 소셜 로그인 후 회원 로그인 패스워드가 1111
        if(memberSecurityDTO.isSocial() && passwordEncoder.matches("1111", memberSecurityDTO.getPwd())){
            log.info("Should Change Password");
            log.info("Redirect to Member Modify");

//            response.sendRedirect("/member/modify");

            return;
        }else{
//            response.sendRedirect("/board/list");
        }

    }
}
