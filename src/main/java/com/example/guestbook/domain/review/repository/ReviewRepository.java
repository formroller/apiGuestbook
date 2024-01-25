package com.example.guestbook.domain.review.repository;

import com.example.guestbook.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
