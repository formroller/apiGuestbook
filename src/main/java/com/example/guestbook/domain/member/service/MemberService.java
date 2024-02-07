package com.example.guestbook.domain.member.service;


import com.example.guestbook.domain.member.dto.MemberDTO;
import com.example.guestbook.domain.member.dto.MemberJoinDTO;
import com.example.guestbook.domain.member.entity.Member;
import com.example.guestbook.domain.member.entity.MemberRole;

public interface MemberService {
    String register(MemberDTO memberDTO);

    /*회원가입 처리*/
    static class MidExistException extends Exception{}

    void join(MemberJoinDTO joinDTO) throws MidExistException;
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

    default Member joinToEntity(MemberJoinDTO joinDTO){
        Member joinMember = Member.builder()
                .mid(joinDTO.getMid())
                .pwd(joinDTO.getPwd())
                .email(joinDTO.getEmail())
                .del(joinDTO.isDel())
                .social(joinDTO.isSocial())
                .build();

        return joinMember;
    }

}
