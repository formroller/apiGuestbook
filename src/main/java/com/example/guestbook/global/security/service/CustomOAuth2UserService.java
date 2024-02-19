package com.example.guestbook.global.security.service;

import com.example.guestbook.domain.member.entity.Member;
import com.example.guestbook.domain.member.entity.MemberRole;
import com.example.guestbook.domain.member.repository.MemberRepository;
import com.example.guestbook.global.security.dto.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("userRequest : "+userRequest);
        log.info(" =============== OAuth2 User ===============");


        ClientRegistration clientRegistration = userRequest.getClientRegistration(); // 클라이언트 추출
        String clientName = clientRegistration.getClientName();

        log.info("NAME : "+clientName);

        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> paramsMap = oAuth2User.getAttributes();

        String email = null;

        switch(clientName){
            case "kakao" -> email = getKakaoEmail(paramsMap);
        }

        log.info("=".repeat(40));
        log.info(email);
        log.info("=".repeat(40));

        return generateDTO(email, paramsMap);

    }

    private MemberSecurityDTO generateDTO(String email, Map<String, Object> paramsMap) {
        Optional<Member> result = memberRepository.findByEmail(email);

        // DB에 없는 이메일
        if(result.isEmpty()){
            // 회원 추가
            Member member = Member.builder()
                    .mid(email)
                    .pwd(passwordEncoder.encode("1111"))
                    .email(email)
                    .social(true)
                    .build();

            member.addRole(MemberRole.USER);
            memberRepository.save(member);

            // MemberSecurityDTO 구성 및 반환
            MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(email, "1111", email, false, true
            , Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));

            memberSecurityDTO.setProps(paramsMap);

            return memberSecurityDTO;
        }else{
            Member member = result.get();
            MemberSecurityDTO memberSecurityDTO =
                    new MemberSecurityDTO(
                            member.getMid(),
                            member.getPwd(),
                            member.getEmail(),
                            member.isDel(),
                            member.isSocial(),
                            member.getRoleSet()
                                    .stream().map(memberRole -> new SimpleGrantedAuthority("ROLE_"+memberRole.name()))
                                    .collect(Collectors.toList())
                    );
            return memberSecurityDTO;
        }
    }

    private String getKakaoEmail(Map<String, Object>paramMap){
        log.info(" ========== KAKAO ============= ");

        Object value = paramMap.get("kakao_account");
        log.info(value);

        LinkedHashMap accountMap = (LinkedHashMap) value;
        String email = (String)accountMap.get("email");

        log.info(" ** email"+email);

        return email;
    }
}
