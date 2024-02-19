package com.example.guestbook.domain.guestbook.service;

import com.example.guestbook.domain.guestbook.dto.GuestbookDTO;
import com.example.guestbook.domain.guestbook.dto.GuestbookReadDTO;
import com.example.guestbook.domain.guestbook.entity.Guestbook;
import com.example.guestbook.domain.guestbook.repository.GuestbookRepository;
import com.example.guestbook.domain.member.entity.Member;
import com.example.guestbook.domain.member.repository.MemberRepository;
import com.example.guestbook.global.page.PageRequestDTO;
import com.example.guestbook.global.page.PageResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
class GuestbookServiceImplTest {
    @Autowired
    private GuestbookService service;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private GuestbookRepository guestbookRepository;

    @DisplayName("등록 테스트")
    @Test
    @Transactional
    public void testRegister(){

        System.out.println(service.getClass().getName());
        GuestbookDTO dto = GuestbookDTO.builder()
                .title("Service Register")
                .content("Service Register test")
                .writerEmail("aa1@aa.com")
                .build();

        dto.setImgNames(
                Arrays.asList(
                        UUID.randomUUID()+"_aaa.jpg",
                        UUID.randomUUID()+"_bbb.jpg",
                        UUID.randomUUID()+"_ccc.jpg"
                ));

        Long gno = service.register(dto);


        System.out.println(gno);
    }

    @Test
    public void testRead(){
        Long gno = 1L;

        System.out.println(service.get(gno));
    }

//    @Test
//    public void testGetList(){
//        PageRequestDTO requestDTO = PageRequestDTO.builder()
//                .page(1)
//                .size(10)
//                .build();
//
//        PageResponseDTO<GuestbookDTO, Object[]> resultDTO = service.getList(requestDTO);
//
//        for(GuestbookDTO guestbookDTO : resultDTO.getDtoList()){
//            System.out.println(guestbookDTO);
//        }
//    }

    @Test
    public void testGetList(){
        PageRequestDTO requestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("tc")
                .keyword("11")
                .build();

        PageResponseDTO<GuestbookDTO, Object[]> resultDTO = service.getList(requestDTO);

        System.out.println("Prev : "+resultDTO.isPrev());
        System.out.println("Next : "+resultDTO.isNext());
        System.out.println("Total "+resultDTO.getTotalPage());

        System.out.println("-".repeat(30));
        for(GuestbookDTO guestbookDTO : resultDTO.getDtoList()){
            System.out.println(guestbookDTO);
        }

        System.out.println("=".repeat(30));
        resultDTO.getPageList().forEach(i->{
            System.out.println(i);
        });
    }



//    @Test
//    public void testSearch(){
//        PageRequestDTO requestDTO = PageRequestDTO.builder()
//                .page(1)
//                .size(10)
//                .type("tc")
//                .keyword("11")
//                .build();
//
//        PageResponseDTO<GuestbookDTO, Object[]> responseDTO = service.getList(requestDTO);
//
//        System.out.println("PREV : "+responseDTO.isPrev());
//        System.out.println("NEXT : "+responseDTO.isNext());
//        System.out.println("TOTAL : "+responseDTO.getTotalPage());
//
//        System.out.println("=".repeat(40));
//        for(GuestbookDTO guestbookDTO : responseDTO.getDtoList()){
//            System.out.println(guestbookDTO);
//        }
//
//        System.out.println("=".repeat(40));
//        responseDTO.getPageList().forEach(i-> System.out.println(i));
//    }
    @Test
    public void testList() {

        //1페이지 10개
        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResponseDTO<GuestbookDTO, Object[]> result = service.getList(pageRequestDTO);

        for (GuestbookDTO boardDTO : result.getDtoList()) {
            System.out.println(boardDTO);
        }

    }

    @Test
    @Transactional
    void modify() {
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .gno(3L)
                .title("제목 변경")
                .content("내용 변경")
                .build();

        service.modify(guestbookDTO);
    }



    @Test
    @Transactional
    void removeWithReviews() {
        Long num = 20L;
        service.removeWithReviews(num);
    }

    @Test
    @Transactional
    void readOne() {
        Long gno = 10L;

        GuestbookReadDTO readDTO = service.readOne(gno);

        System.out.println(readDTO);

        readDTO.getImgNames().forEach(System.out::println);

    }

    @Test
    @Transactional
    void testModify() {
        // 변경 필요한 데이터
        GuestbookDTO dto = GuestbookDTO.builder()
                .gno(925L)
                .title("Modify 102")
                .content("Update Content 102")
                .build();

        // 첨부파일 추가
        dto.setImgNames(Arrays.asList(UUID.randomUUID().toString(), "_abc.jpg"));

        service.modify(dto);
    }
}