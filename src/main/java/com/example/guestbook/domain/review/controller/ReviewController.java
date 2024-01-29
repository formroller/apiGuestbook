package com.example.guestbook.domain.review.controller;

import com.example.guestbook.domain.review.dto.ReviewDTO;
import com.example.guestbook.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Log4j2
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/guestbook/{gno}")
    public ResponseEntity<List<ReviewDTO>> getListByGuestbook(@PathVariable("gno") Long gno){
        log.info("Review Controller - getListByGuestbook gno : "+gno);

        return new ResponseEntity<>(reviewService.getList(gno), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody ReviewDTO reviewDTO){
        log.info("Review Controller - register reviewDTO "+reviewDTO);

        Long rno = reviewService.register(reviewDTO);

        return new ResponseEntity<>(rno, HttpStatus.OK);
    }

    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno")Long rno){
        log.info("Review Controller rno : "+rno);
        reviewService.remove(rno);

        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @PutMapping("/{rno}")
    public ResponseEntity<String> modify(@RequestBody ReviewDTO reviewDTO){
        log.info("Review Controller Modify reviewDTO :"+reviewDTO);
        reviewService.modify(reviewDTO);

        return new ResponseEntity<>("Changed", HttpStatus.OK);
    }

}
