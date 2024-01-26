package com.example.guestbook.domain.review.service;

import com.example.guestbook.domain.guestbook.entity.Guestbook;
import com.example.guestbook.domain.review.dto.ReviewDTO;
import com.example.guestbook.domain.review.entity.Review;
import com.example.guestbook.domain.review.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository repository;

    @Override
    public Long register(ReviewDTO reviewDTO) {
        Review review = toEntity(reviewDTO);

        repository.save(review);

        return review.getRno();
    }

    @Override
    public List<ReviewDTO> getList(Long gno) {
        List<Review> reviewList = repository.getReviewsByGuestbookOrderByRno(Guestbook.builder().gno(gno).build());

        return reviewList.stream().map(review -> toDTO(review)).collect(Collectors.toList());
    }

    @Override
    public void modify(ReviewDTO reviewDTO) {
        Review review = toEntity(reviewDTO);

        if(review != null){
            repository.save(review);
        }
    }

    @Override
    public void remove(Long rno) {

    }
}
