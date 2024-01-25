package com.example.guestbook.domain.guestbook.controller;

import com.example.guestbook.domain.guestbook.dto.GuestbookDTO;
import com.example.guestbook.domain.guestbook.entity.Guestbook;
import com.example.guestbook.domain.guestbook.service.GuestbookService;
import com.example.guestbook.global.page.PageRequestDTO;
import com.example.guestbook.global.page.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guestbook")
@Log4j2
public class GuestbookController {
    private final GuestbookService guestbookService;

    @PostMapping("/register")
    public ResponseEntity<Long> register(GuestbookDTO guestbookDTO){

        log.info(guestbookDTO);


        Long num = guestbookService.register(guestbookDTO);

        return new ResponseEntity<>(num,HttpStatus.OK);
    }

    // 조회
    @GetMapping("/read/{gno}")
    public ResponseEntity<GuestbookDTO> read(@PathVariable("gno") Long gno){
        GuestbookDTO dto = guestbookService.read(gno);

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<PageResponseDTO<GuestbookDTO, Guestbook>> list(PageRequestDTO requestDTO){

        PageResponseDTO<GuestbookDTO, Guestbook> result = guestbookService.getList(requestDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/search")
        public ResponseEntity<List<GuestbookDTO>> getSearch( PageRequestDTO requestDTO){
        PageResponseDTO<GuestbookDTO, Guestbook> responseDTO = guestbookService.getListSecond(requestDTO);
        List<GuestbookDTO> result = responseDTO.getDtoList();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // todo : 커스텀 JPA Repository 통한 검색 설정(https://ttl-blog.tistory.com/380 참고할 것)
//    public ResponseEntity search(Pageable pageable, @ModelAttribute PageRequestDTO requestDTO){
//        return new ResponseEntity(guestbookService.getListSecond(requestDTO), HttpStatus.OK);
//        return ResponseEntity.OK()
//    }


    @DeleteMapping("/{gno}")
    public ResponseEntity<Long> remove(@PathVariable("gno") Long gno){
        guestbookService.remove(gno);

        return new ResponseEntity(gno+" has deleted",HttpStatus.OK);
    }

}
