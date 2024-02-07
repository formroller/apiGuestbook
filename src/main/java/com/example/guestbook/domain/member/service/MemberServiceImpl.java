package com.example.guestbook.domain.member.service;

import com.example.guestbook.domain.member.dto.MemberDTO;
import com.example.guestbook.domain.member.dto.MemberJoinDTO;
import com.example.guestbook.domain.member.entity.Member;
import com.example.guestbook.domain.member.entity.MemberRole;
import com.example.guestbook.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public String register(MemberDTO memberDTO) {
        log.info(memberDTO);

        Member entity = toEntity(memberDTO);

        memberRepository.save(entity);

        return entity.getEmail();

    }

    @Override
    public void join(MemberJoinDTO joinDTO) throws MidExistException {
        String mid = joinDTO.getMid();

        boolean exists = memberRepository.existsById(mid);

        if(exists){
            throw new MidExistException();
        }

        Member member = joinToEntity(joinDTO);
        member.changePwd(passwordEncoder.encode(member.getPwd()));
        member.addRole(MemberRole.USER);

        log.info("================");
        log.info(member);
        log.info(member.getRoleSet());

        memberRepository.save(member);

    }

}
