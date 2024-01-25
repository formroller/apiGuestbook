package com.example.guestbook.domain.guestbook.service;


import com.example.guestbook.domain.guestbook.dto.GuestbookDTO;
import com.example.guestbook.domain.guestbook.entity.Guestbook;
import com.example.guestbook.domain.member.entity.Member;
import com.example.guestbook.global.page.PageRequestDTO;
import com.example.guestbook.global.page.PageResponseDTO;
import com.querydsl.core.BooleanBuilder;

public interface GuestbookService {
    /*CRUD*/
    Long register(GuestbookDTO guestbookDTO);

    GuestbookDTO read(Long gno);

    PageResponseDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO);
    PageResponseDTO<GuestbookDTO, Guestbook> getListSecond(PageRequestDTO requestDTO);

    void modify(GuestbookDTO guestbookDTO);

    void remove(Long gno);

//    BooleanBuilder getSearch(PageRequestDTO requestDTO);

    /* 직렬화, 역직렬화*/
    default Guestbook toEntity(GuestbookDTO guestbookDTO){
        Member member = Member.builder().mid(guestbookDTO.getWriterEmail()).build();

        Guestbook guestbook = Guestbook.builder()
                .gno(guestbookDTO.getGno())
                .title(guestbookDTO.getTitle())
                .content(guestbookDTO.getContent())
//                .writer(member)
                .writer(guestbookDTO.getWriterEmail())
                .build();

        return guestbook;

    }

    default GuestbookDTO toDTO(Guestbook guestbook){
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .gno(guestbook.getGno())
                .title(guestbook.getTitle())
                .content(guestbook.getContent())
                .regDate(guestbook.getRegDate())
                .modDate(guestbook.getModDate())
//                .writerEmail(member.getMid())
//                .replyCnt(replyCnt.intValue())
                .build();

        return guestbookDTO;
    }
}
