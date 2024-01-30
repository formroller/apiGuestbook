package com.example.guestbook.domain.review.repository;

import com.example.guestbook.domain.guestbook.entity.Guestbook;
import com.example.guestbook.domain.member.entity.Member;
import com.example.guestbook.domain.review.entity.Review;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
//    @DisplayName("댓글 등록")
//    @Test
//    public void insertReview(){
//        IntStream.rangeClosed(1,100).forEach(i->{
//            long gno = (long) (Math.random()*100)+1;
//
//            Guestbook guestbook = Guestbook.builder().gno(gno).build();
////
////            Random random = new Random();
////            int user = random.nextInt(1,400);
////            Member member = Member.builder().email("aa"+user+"@aa.com").build();
//
//            Review review = Review.builder()
//                    .guestbook(guestbook)
//                    .reviewer("guest")
//                    .text("댓글 등록 "+i)
//                    .build();
//
//            repository.save(review);
//            System.out.println(review);
//        });
//    }


    @DisplayName("댓글 등록 (게시판, 회원 포함)")
    @Test
    public void insertReviews(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Long gno = (long)(Math.random()*100)+i;

//             리뷰어 번호
            Long mno = ((long)(Math.random()*100)+1);
            Member member = Member.builder().mno(mno).build();

            Review review = Review.builder()
                    .guestbook(Guestbook.builder().gno(gno).build())
                    .member(member)
                    .text("댓글 번호 : "+i)
                    .build();

            repository.save(review);
        });
    }

   @DisplayName("게시판에 달린 댓글 가져오기 (내림차순)")
   @Test
    public void testListByBoard(){
        List<Review> reviewList = repository.getReviewsByGuestbookOrderByRno(Guestbook.builder().gno(100L).build());

       reviewList.forEach(System.out::println);
   }

}