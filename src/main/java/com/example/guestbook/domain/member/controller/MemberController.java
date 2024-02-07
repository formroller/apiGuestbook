package com.example.guestbook.domain.member.controller;

import com.example.guestbook.domain.member.dto.MemberJoinDTO;
import com.example.guestbook.domain.member.service.MemberService;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@Log4j2
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService service;


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


    @GetMapping("/join")
    public ResponseEntity<Void> joinGet(){
        log.info("Join Get");
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/join")
    public ResponseEntity<MemberJoinDTO> joinPost(MemberJoinDTO joinDTO, RedirectAttributes redirectAttributes){
        log.info("JoinDTO : "+joinDTO);

        try{
            service.join(joinDTO);
        } catch (MemberService.MidExistException e){
            redirectAttributes.addAttribute("error", "mid");
        }
        redirectAttributes.addFlashAttribute("result", "success");

        return new ResponseEntity<>(joinDTO, HttpStatus.OK);
    }
}
