package com.example.guestbook.domain.member.service;


import com.example.guestbook.domain.member.dto.MemberDTO;
import com.example.guestbook.domain.member.entity.Member;

public interface MemberService {
    Long register(MemberDTO memberDTO);


    /* 직렬화 및 역직렬화 */
    default Member toEntity(MemberDTO dto){

        Member member = Member.builder()
                .mno(dto.getMno())
                .mid(dto.getMid())
                .pwd(dto.getPwd())
                .phone(dto.getPhone())
                .build();

        return member;
    }

    default MemberDTO toDTO(Member member){

        MemberDTO dto = MemberDTO.builder()
                .mno(member.getMno())
                .mid(member.getMid())
                .pwd(member.getPwd())
                .phone(member.getPhone())
                .build();

        return dto;
    }

}
