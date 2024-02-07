package com.example.guestbook.domain.guestbook.service;


import com.example.guestbook.domain.guestbook.dto.GuestbookDTO;
import com.example.guestbook.domain.guestbook.dto.GuestbookListAllDTO;
import com.example.guestbook.domain.guestbook.dto.GuestbookReadDTO;
import com.example.guestbook.domain.guestbook.entity.Guestbook;
import com.example.guestbook.domain.member.entity.Member;
import com.example.guestbook.global.page.PageRequestDTO;
import com.example.guestbook.global.page.PageResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public interface GuestbookService {
    /*CRUD*/
    Long register(GuestbookDTO guestbookDTO);

    GuestbookDTO get(Long gno);

    GuestbookReadDTO readOne(Long gno);

    PageResponseDTO<GuestbookDTO, Object[]> getList(PageRequestDTO requestDTO);

    void modify(GuestbookDTO guestbookDTO);

    void remove(Long gno);

    void removeWithReviews(Long gno);



    // 게시글 이미지 및 댓글 숫자 처리
    PageResponseDTO<GuestbookListAllDTO, Object[]> listWithAll(PageRequestDTO requestDTO);

    /* 직렬화, 역직렬화*/
    default Guestbook toEntity(GuestbookDTO guestbookDTO){
        Member member = Member.builder().email(guestbookDTO.getWriterEmail()).build();

        Guestbook guestbook = Guestbook.builder()
                .gno(guestbookDTO.getGno())
                .title(guestbookDTO.getTitle())
                .content(guestbookDTO.getContent())
                .writer(guestbookDTO.getWriter())
                .build();

        if(guestbookDTO.getImgNames() != null){
            guestbookDTO.getImgNames().forEach(imageName ->{
                String[] arr = imageName.split("");
                guestbook.addImage(arr[0], arr[1]);
            });
        }
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

        // 게시물 조회 처리
        List<String> imgName =
                guestbook.getImageSet().stream().map(guestbookImg ->
                    guestbookImg.getUuid()+"_"+guestbookImg.getImgName()).collect(Collectors.toList());

        guestbookDTO.setImgNames(imgName);

        return guestbookDTO;
    }

    default GuestbookReadDTO toReadDTO(Guestbook guestbook){

        GuestbookReadDTO readDTO = GuestbookReadDTO.builder()
                .gno(guestbook.getGno())
                .content(guestbook.getContent())
                .title(guestbook.getTitle())
                .writer(String.valueOf(guestbook.getWriter()))
                .regDate(guestbook.getRegDate())
                .modDate(guestbook.getModDate())
                .build();

        // 게시물 조회 처리
        List<String> imgName =
                guestbook.getImageSet().stream().map(guestbookImg ->
                        guestbookImg.getUuid()+"_"+guestbookImg.getImgName()).collect(Collectors.toList());

        readDTO.setImgNames(imgName);

        return readDTO;
    }
}
