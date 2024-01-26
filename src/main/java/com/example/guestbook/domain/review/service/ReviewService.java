package com.example.guestbook.domain.review.service;

import com.example.guestbook.domain.guestbook.entity.Guestbook;
import com.example.guestbook.domain.review.dto.ReviewDTO;
import com.example.guestbook.domain.review.entity.Review;

import java.util.List;

public interface ReviewService {
    //todo 댓글 등록
    Long register(ReviewDTO reviewDTO);

    //todo 특정 게시글의 댓글 리스트 가져오기
    List<ReviewDTO> getList(Long gno);

    //todo 댓글 수정 및 삭제
    void modify(ReviewDTO reviewDTO);
    void remove(Long rno);


    //todo 직렬화, 역직렬화
    default Review toEntity(ReviewDTO reviewDTO){
        Guestbook guestbook = Guestbook.builder().gno(reviewDTO.getGno()).build();

        Review review = Review.builder()
                .rno(reviewDTO.getRno())
                .reviewer(reviewDTO.getReviewer())
                .text(reviewDTO.getText())
                .guestbook(guestbook)
                .build();
        return review;
    }


    default ReviewDTO toDTO(Review review){
        ReviewDTO dto = ReviewDTO.builder()
                .rno(review.getRno())
                .reviewer(review.getReviewer())
                .text(review.getText())
                .regDate(review.getRegDate())
                .build();

        return dto;
    }

}
