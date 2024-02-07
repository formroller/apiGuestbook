package com.example.guestbook.domain.guestbook.controller;

import com.example.guestbook.domain.guestbook.dto.GuestbookDTO;
import com.example.guestbook.domain.guestbook.entity.Guestbook;
import com.example.guestbook.domain.guestbook.service.GuestbookService;
import com.example.guestbook.global.page.PageRequestDTO;
import com.example.guestbook.global.page.PageResponseDTO;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guestbook")
@Log4j2
public class GuestbookController {
    private final GuestbookService guestbookService;

    @Value("${com.example.guestbook.path}")
    private String imagePath;


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/register")
    public ResponseEntity<Void> register(GuestbookDTO guestbookDTO){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Long> register(GuestbookDTO guestbookDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        log.info(guestbookDTO);
        if(bindingResult.hasErrors()){
            log.info("Has Error");

            redirectAttributes.addAttribute("errors", bindingResult.getAllErrors());
        }


        Long num = guestbookService.register(guestbookDTO);

        return new ResponseEntity<>(num,HttpStatus.OK);
    }

    // 조회
    @GetMapping("/read/{gno}")
    public ResponseEntity<GuestbookDTO> read(@PathVariable("gno") Long gno){
        GuestbookDTO dto = guestbookService.get(gno);

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<PageResponseDTO<GuestbookDTO, Object[]>> list(PageRequestDTO requestDTO){

        PageResponseDTO<GuestbookDTO, Object[]> result = guestbookService.getList(requestDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    // todo : 커스텀 JPA Repository 통한 검색 설정(https://ttl-blog.tistory.com/380 참고할 것)
//    public ResponseEntity search(Pageable pageable, @ModelAttribute PageRequestDTO requestDTO){
//        return new ResponseEntity(guestbookService.getListSecond(requestDTO), HttpStatus.OK);
//        return ResponseEntity.OK()
//    }


    @DeleteMapping("/{gno}")
    public ResponseEntity<Long> remove(@PathVariable("gno") Long gno){
        guestbookService.removeWithReviews(gno);

        return new ResponseEntity(gno+" has deleted",HttpStatus.OK);
    }


    // login
    @GetMapping("/login")
    public ResponseEntity<Null> loginGet(String errorCode, String logout){
        log.info("==== login get ====");
        log.info("logout : "+logout);

        if(logout != null){
            log.info("=== user logout ===");
        }
        return null;
    }
}
