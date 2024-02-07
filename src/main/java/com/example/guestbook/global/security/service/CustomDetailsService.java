package com.example.guestbook.global.security.service;

import com.example.guestbook.domain.member.entity.Member;
import com.example.guestbook.domain.member.repository.MemberRepository;
import com.example.guestbook.global.security.dto.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
//    private PasswordEncoder passwordEncoder;

//    public CustomDetailsService(){
//        this.passwordEncoder = new BCryptPasswordEncoder();
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("loadUserByUsername : " + username);

        Optional<Member> result = memberRepository.getWithRoles(username);
        
        if(result.isEmpty()){
            throw new UsernameNotFoundException("Username not fount");
        }

        Member member = result.get();

        MemberSecurityDTO securityDTO = new MemberSecurityDTO(
                member.getMid(),
                member.getPwd(),
                member.getEmail(),
                member.isDel(),
                false,
                member.getRoleSet()
                        .stream().map(memberRole -> new SimpleGrantedAuthority("ROLE_"+memberRole.name())).collect(Collectors.toList())
        );

        log.info("securityDTO");
        log.info(securityDTO);

        return securityDTO;


//        UserDetails userDetails = User.builder()
//                .username("user1")
//                .password(passwordEncoder.encode("1111"))
//                .authorities("ROLE_USER")
//                .build();
//
//        log.info(" === User Details :"+userDetails+" ===");
//
//        return userDetails;
    }
}
