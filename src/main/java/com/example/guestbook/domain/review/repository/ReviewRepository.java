package com.example.guestbook.domain.review.repository;

import com.example.guestbook.domain.guestbook.entity.Guestbook;
import com.example.guestbook.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Board 삭제시 댓글 삭제
    @Modifying
    @Query("delete from Review r where r.guestbook.gno=:gno")
    void deleteByGno(Long gno);

    // 게시물로 댓글 가져오기
    List<Review> getReviewsByGuestbookOrderByRno(Guestbook guestbook);

}
