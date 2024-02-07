package com.example.guestbook.domain.member.service;


import com.example.guestbook.domain.member.dto.MemberDTO;
import com.example.guestbook.domain.member.entity.Member;

public interface MemberService {
    String register(MemberDTO memberDTO);


    /* 직렬화 및 역직렬화 */
    default Member toEntity(MemberDTO dto){

        Member member = Member.builder()

                .email(dto.getEmail())
                .pwd(dto.getPwd())
                .build();

        return member;
    }

    default MemberDTO toDTO(Member member){

        MemberDTO dto = MemberDTO.builder()
                .email(member.getEmail())
                .pwd(member.getPwd())
                .build();

        return dto;
    }

}
