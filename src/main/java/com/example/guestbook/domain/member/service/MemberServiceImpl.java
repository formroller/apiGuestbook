package com.example.guestbook.domain.member.service;

import com.example.guestbook.domain.member.dto.MemberDTO;
import com.example.guestbook.domain.member.entity.Member;
import com.example.guestbook.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private MemberRepository memberRepository;

    @Override
    public Long register(MemberDTO memberDTO) {
        log.info(memberDTO);

        Member entity = toEntity(memberDTO);

        memberRepository.save(entity);

        return entity.getMno();

    }

}
