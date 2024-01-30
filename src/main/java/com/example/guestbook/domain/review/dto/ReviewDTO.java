package com.example.guestbook.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewDTO {
    private Long rno;
    private String text;
    private String reviewer;
    private Long gno; // 게시글 번호
    private LocalDateTime regDate, modDate;
}