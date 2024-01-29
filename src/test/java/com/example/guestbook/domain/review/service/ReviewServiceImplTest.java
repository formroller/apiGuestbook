package com.example.guestbook.domain.review.service;

import com.example.guestbook.domain.review.dto.ReviewDTO;
import com.example.guestbook.domain.review.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewServiceImplTest {
    @Autowired
    private ReviewService service;

    @Test
    void register() {

    }

    @DisplayName("댓글 목록 가져오기")
    @Test
    void getList() {
        Long gno = 99L;
        List<ReviewDTO> resultDTOList = service.getList(gno);
        resultDTOList.forEach(System.out::println);
    }

    @Test
    void modify() {
    }

    @Test
    void remove() {
    }
}