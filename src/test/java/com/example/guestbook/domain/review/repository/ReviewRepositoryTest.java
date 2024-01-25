package com.example.guestbook.domain.review.repository;

import com.example.guestbook.domain.guestbook.entity.Guestbook;
import com.example.guestbook.domain.review.entity.Review;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@SpringBootTest
class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository repository;

    @Test
    public void testRnd(){
        IntStream.rangeClosed(1,100).forEach(i->{
            System.out.println((long) (Math.random()*100)+1);
        });

    }
    @DisplayName("댓글 등록")
    @Transactional
    @Test
    public void insertReview(){
        IntStream.rangeClosed(1,300).forEach(i->{
            long gno = (long) (Math.random()*100)+1;

            Guestbook guestbook = Guestbook.builder().gno(gno).build();
            System.out.println(guestbook);

            Review review = Review.builder()
                    .text("댓글 등록 "+i)
                    .guestbook(guestbook)
                    .reviewer("guest")
                    .build();

            repository.save(review);
        });
    }

}