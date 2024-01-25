package com.example.guestbook.domain.guestbook.service;

import com.example.guestbook.domain.guestbook.dto.GuestbookDTO;
import com.example.guestbook.domain.guestbook.entity.Guestbook;
import com.example.guestbook.global.page.PageRequestDTO;
import com.example.guestbook.global.page.PageResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GuestbookServiceImplTest {
    @Autowired
    private GuestbookService service;

    @DisplayName("등록 테스트")
    @Test
    public void testRegister(){
        GuestbookDTO dto = GuestbookDTO.builder()
                .title("Service Register")
                .content("Service Register test")
                .writerEmail("aa102@aa.com")
                .build();

        System.out.println(service.register(dto));
    }

    @Test
    public void testRead(){
        Long gno = 1L;

        System.out.println(service.read(gno));
    }

    @Test
    public void testGetList(){
        PageRequestDTO requestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(requestDTO);

        for(GuestbookDTO guestbookDTO : resultDTO.getDtoList()){
            System.out.println(guestbookDTO);
        }
    }

    @Test
    public void testGetListSecond(){
        PageRequestDTO requestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("tc")
                .keyword("11")
                .build();

        PageResponseDTO<GuestbookDTO, Guestbook> resultDTO = service.getListSecond(requestDTO);

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



    @Test
    public void testSearch(){
        PageRequestDTO requestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("tc")
                .keyword("11")
                .build();

        PageResponseDTO<GuestbookDTO, Guestbook> responseDTO = service.getList(requestDTO);

        System.out.println("PREV : "+responseDTO.isPrev());
        System.out.println("NEXT : "+responseDTO.isNext());
        System.out.println("TOTAL : "+responseDTO.getTotalPage());

        System.out.println("=".repeat(40));
        for(GuestbookDTO guestbookDTO : responseDTO.getDtoList()){
            System.out.println(guestbookDTO);
        }

        System.out.println("=".repeat(40));
        responseDTO.getPageList().forEach(i-> System.out.println(i));

    }

    @Test
    void modify() {
    }

    @Test
    void remove() {
        Long gno = 100L;

        service.remove(gno);
    }
}