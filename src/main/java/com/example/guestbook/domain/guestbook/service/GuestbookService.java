package com.example.guestbook.domain.guestbook.service;


import com.example.guestbook.domain.guestbook.dto.GuestbookDTO;
import com.example.guestbook.domain.guestbook.entity.Guestbook;
import com.example.guestbook.domain.member.entity.Member;
import com.example.guestbook.global.page.PageRequestDTO;
import com.example.guestbook.global.page.PageResponseDTO;

public interface GuestbookService {
    /*CRUD*/
    Long register(GuestbookDTO guestbookDTO);

    GuestbookDTO get(Long gno);

    PageResponseDTO<GuestbookDTO, Object[]> getList(PageRequestDTO requestDTO);

    void modify(GuestbookDTO guestbookDTO);

    void removeWithReviews(Long gno);

    /* 직렬화, 역직렬화*/
    default Guestbook toEntity(GuestbookDTO guestbookDTO){
        Member member = Member.builder().email(guestbookDTO.getWriterEmail()).build();

        Guestbook guestbook = Guestbook.builder()
                .gno(guestbookDTO.getGno())
                .title(guestbookDTO.getTitle())
                .content(guestbookDTO.getContent())
                .writer(member)
                .build();

        return guestbook;

    }

    default GuestbookDTO toDTO(Guestbook guestbook, Member member, Long reviewCnt){

        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .gno(guestbook.getGno())
                .title(guestbook.getTitle())
                .content(guestbook.getContent())
                .regDate(guestbook.getRegDate())
                .modDate(guestbook.getModDate())
                .writerEmail(member.getEmail())
                .reviewCnt(reviewCnt.intValue())
                .build();

        return guestbookDTO;
    }
}
